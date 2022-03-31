import { Injectable } from '@angular/core';
import { HttpClient, HttpEventType, HttpHeaders, HttpResponse } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { User } from './user';
import { Monkey } from './monkey';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersUrl = 'http://localhost:8080/users';

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl)
  }

  getUserById(id: number): Observable<User> {

    return this.http.get<User>(`${this.usersUrl}/${id}`).pipe(
      //tap((newUser: User) => this.log(`found user w/ id=${newUser.id}`)),
      catchError(this.handleError<User>('getUserById'))
    );
  }

  getUserCart(id: number): Observable<Monkey[]>{
    return this.http.get<Monkey[]>(`${this.usersUrl}/cart/${id}`);
  }

  getUserByName(username: string): Observable<User> {
    if (!username.trim()) {
      // if not search term, return empty hero.
      return of();
    }
    return this.http.get<User>(`${this.usersUrl}/?username=${username}`).pipe(
      //tap((newUser: User) => this.log(`found user w/ username=${newUser.username}`)),
      catchError(this.handleError<User>('getUserByName'))
    );
  }

  getUserByNameWithResponse(username: string): Observable<HttpResponse<User>> {
    const defaultUser: User = {id:0, username:"default", cartList:[], rentedList:[]};
    
    if (!username.trim()) {
      // if not search term, return empty response.
      return of();
    }
    return this.http.get<User>(`${this.usersUrl}/?username=${username}`, { observe: 'response'}).pipe(
      catchError(err => {      
        var errResponse: HttpResponse<User> = {
          body: defaultUser,
          status: err.status,
          statusText: "",
          url: "",
          type: HttpEventType.Response,
          clone: function (): HttpResponse<User> {
            throw new Error('Function not implemented.');
          },
          ok: false,
          headers: new HttpHeaders
        };
        return of(errResponse);
      })
    );
  }

  addUser(user: User): Observable<User> {
    return this.http.post<User>(this.usersUrl, user, this.httpOptions).pipe(
      //tap((newUser: User) => this.log(`added user w/ id=${newUser.id}`)),
      catchError(this.handleError<User>('addUser'))
    );
  }

  addUserWithResponse(user: User): Observable<HttpResponse<User>> {
    const defaultUser: User = {id:0, username:"default", cartList:[], rentedList:[]};
    
    return this.http.post<User>(this.usersUrl, user, { observe: 'response'}).pipe(
      catchError(err => {      
        var errResponse: HttpResponse<User> = {
          body: defaultUser,
          status: err.status,
          statusText: "",
          url: "",
          type: HttpEventType.Response,
          clone: function (): HttpResponse<User> {
            throw new Error('Function not implemented.');
          },
          ok: false,
          headers: new HttpHeaders
        };
        return of(errResponse);
      })
    );
  }

  updateUser(user: User): Observable<any> {
    return this.http.put(this.usersUrl, user, this.httpOptions).pipe(
      //tap(_ => this.log(`updated user id=${user.id}`)),
      catchError(this.handleError<any>('updateUser'))
    );
  }
  /*
  private log(message: string) {
    this.messageService.add(`MonkeyService: ${message}`);
  }
  */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      //this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  constructor(
    private http: HttpClient,
    //private messageService: MessageService
    ) { }
}