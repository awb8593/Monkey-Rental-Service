import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Review } from './review';
import { Monkey } from './monkey';


@Injectable({
  providedIn: 'root'
})

export class ReviewService {


  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(
    
  ) {}

  reviews: Review[] = [];

  addReview(review: Review): Observable<Review> {
    return this.http.post<Review>(this.monkeysUrl, monkey, this.httpOptions).pipe(
      tap((newMonkey: Monkey) => this.log(`added monkey w/ id=${newMonkey.id}`)),
      catchError(this.handleError<Monkey>('addMonkey'))
    );
  }

}
