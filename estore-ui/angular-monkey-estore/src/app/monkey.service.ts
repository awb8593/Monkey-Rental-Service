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

}
