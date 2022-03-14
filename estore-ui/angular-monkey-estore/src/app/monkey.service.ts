import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Monkey } from './monkey';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class MonkeyService {

  private monkeysUrl = 'http://localhost:8080/monkeys';

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  addMonkey(monkey: Monkey): Observable<Monkey> {
    return this.http.post<Monkey>(this.monkeysUrl, monkey, this.httpOptions).pipe(
      tap((newMonkey: Monkey) => this.log(`added monkey w/ id=${newMonkey.id}`)),
      catchError(this.handleError<Monkey>('addMonkey'))
    );
  }

  deleteMonkey(id: number): Observable<Monkey> {
    const url = `${this.monkeysUrl}/${id}`;

    return this.http.delete<Monkey>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted monkey id=${id}`)),
      catchError(this.handleError<Monkey>('deleteMonkey'))
    );
  }

  updateMonkey(monkey: Monkey): Observable<any> {
    return this.http.put(this.monkeysUrl, monkey, this.httpOptions).pipe(
      tap(_ => this.log(`updated monkey id=${monkey.id}`)),
      catchError(this.handleError<any>('updateMonkey'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  private log(message: string) {
    this.messageService.add(`MonkeyService: ${message}`);
  }

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  getMonkeys(): Observable<Monkey[]> {
    return this.http.get<Monkey[]>(this.monkeysUrl)
  }
}
