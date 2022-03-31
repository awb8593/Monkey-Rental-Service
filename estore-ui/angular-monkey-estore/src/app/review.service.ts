import { Review } from './review';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { MessageService } from './message.service';


@Injectable({
  providedIn: 'root'
})

export class ReviewService {

  private reviewsUrl = 'http://localhost:8080/reviews';

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  reviews: Review[] = [];

  addUser(user: Review): Observable<Review> {
    return this.http.post<Review>(this.reviewsUrl, user, this.httpOptions).pipe(
      //tap((newUser: User) => this.log(`added user w/ id=${newUser.id}`)),
      catchError(this.handleError<Review>('addReview'))
    );
  }

  getReviews(): Observable<Review[]> {
    return this.http.get<Review[]>(this.reviewsUrl)
  }

  getReviewById(id: number): Observable<Review> {

    return this.http.get<Review>(`${this.reviewsUrl}/${id}`).pipe(
      //tap((newUser: User) => this.log(`found user w/ id=${newUser.id}`)),
      catchError(this.handleError<Review>('getReviewById'))
    );
  }

  private log(message: string) {
    this.messageService.add(`ReviewService: ${message}`);
  }

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
}
