import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Rental } from './rental';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class RentalService {

  private rentalsUrl = 'http://localhost:8080/rentals';

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  addRental(rental: Rental): Observable<Rental> {
    return this.http.post<Rental>(this.rentalsUrl, rental, this.httpOptions).pipe(
      tap((newRental: Rental) => this.log(`added rental w/ id=${newRental.id}`)),
      catchError(this.handleError<Rental>('addRental'))
    );
  }

  deleteRental(id: number): Observable<Rental> {
    const url = `${this.rentalsUrl}/${id}`;

    return this.http.delete<Rental>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted rental id=${id}`)),
      catchError(this.handleError<Rental>('deleteRental'))
    );
  }

  updateRental(rental: Rental): Observable<any> {
    return this.http.put(this.rentalsUrl, rental, this.httpOptions).pipe(
      tap(_ => this.log(`updated rental id=${rental.id}`)),
      catchError(this.handleError<any>('updateRental'))
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
    this.messageService.add(`RentalService: ${message}`);
  }

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  getAllRentals(onlyActive: boolean): Observable<Rental[]> {
    const url = `${this.rentalsUrl}/${onlyActive}`;
    return this.http.get<Rental[]>(url)
  }

  getRental(id: number): Observable<Rental> {
    const url = `${this.rentalsUrl}/?id=${id}`;
    return this.http.get<Rental>(url);
  }

  getRentalsUser(userId: number, onlyActive: boolean): Observable<Rental[]> {
    const url = `${this.rentalsUrl}/${userId}/${onlyActive}`;
    return this.http.get<Rental[]>(url);
  }

  setRentalDateString(rental: Rental, dateStr: string): Observable<Rental> {
    rental.rentalDate = new Date(dateStr).toJSON()
    return of(rental);
  }

  setReturnDateString(rental: Rental, dateStr: string): Observable<Rental> {
    rental.returnDate = new Date(dateStr).toJSON()
    return of(rental);
  }

  getRentalDateString(rental: Rental): Observable<string> { 
    return of(new Date(rental.rentalDate).toDateString());
  }

  getReturnDateString(rental: Rental): Observable<string> { 
    return of(new Date(rental.returnDate).toDateString());
  }
}
