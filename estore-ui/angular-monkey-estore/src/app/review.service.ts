import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Review } from './review';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private reviewsUrl = 'http://localhost:8080/reviews';

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(
    private http: HttpClient
  ) { }

  getReviewObject(id: number): Observable<Review> {
    return this.http.get<Review>(`${this.reviewsUrl}/${id}`);
  }

  updateReviewObject(review: Review): Observable<any> {
    return this.http.put(this.reviewsUrl, review, this.httpOptions);
  }

  createReviewObject(review: Review): Observable<Review> {
    return this.http.post<Review>(this.reviewsUrl, review, this.httpOptions);
  }
}
