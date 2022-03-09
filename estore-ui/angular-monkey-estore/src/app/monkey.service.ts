import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Monkey } from './monkey';

@Injectable({
  providedIn: 'root'
})
export class MonkeyService {

  private monkeysUrl = 'http://localhost:8080/monkeys';

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(
    private http: HttpClient) { }

  getMonkeys(): Observable<Monkey[]> {
    return this.http.get<Monkey[]>(this.monkeysUrl)
  }

  getMonkey(id: number): Observable<Monkey> {
    const url = `${this.monkeysUrl}/${id}`;
    return this.http.get<Monkey>(url);
  }

  searchMonkeys(term: string): Observable<Monkey[]> {
    if (!term.trim()){
      return of([]);
    }
    return this.http.get<Monkey[]>(`${this.monkeysUrl}/?name=${term}`).pipe(
      tap(x => x.length ?
        this.log(`no found monkeys matching "${term}"`) :
        this.log(`no monkeys matching "${term}"`)),
        catchError(this.handleError<Monkey[]>('searchMonkeys', []))
    );

  }

  private handleError<T>(operation = 'operation', result?: T){
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }

  private log(message: string){
  //  this.messageService.add(`MonkeyService: ${message}`);
  }
}
