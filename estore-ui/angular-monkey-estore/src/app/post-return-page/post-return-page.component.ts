import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';

import { Monkey } from '../monkey';
import { Review } from '../review';
import { MonkeyService } from '../monkey.service';
import { ReviewService } from '../review.service';


@Component({
  selector: 'app-post-return-page',
  templateUrl: './post-return-page.component.html',
  styleUrls: ['./post-return-page.component.css']
})
export class PostReturnPageComponent implements OnInit {
  monkey?: Monkey;
  review?: Review;
  message: String = "";
  rating: number = 1;
  comment: String = "";

  constructor(
    private monkeyService : MonkeyService,
    private reviewService : ReviewService,
    private route: ActivatedRoute,
    private location: Location,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.getMonkey();
    this.getReviewObject();
  }

  getMonkey(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.monkeyService.getMonkey(id)
    .subscribe(monkey => this.monkey = monkey);
  }

  getReviewObject(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.reviewService.getReviewObject(id)
    .subscribe(review => this.review = review);
  }

  updateReview(): void {
    if (this.comment == "" && this.rating == null){
      this.message = "Enter a rating and a review";
    }
    else if (this.comment == ""){
      this.message = "Enter a Review";
    }
    else if (this.rating == null){
      this.message = "Enter a Rating"
    }
    else if (this.rating > 5 || this.rating < 1){
      this.message = "Invalid Rating. Rating must be between 1 and 5";
    }
    else if (this.review != undefined) {
      this.message = "success";
      this.review?.reviews.push(this.comment);
      this.review?.ratings.push(this.rating);
      this.reviewService.updateReviewObject(this.review).subscribe();
      this.router.navigate(["/buyer-product-list"]);
    }
    else {
      let newReview: Review = {id:Number(this.route.snapshot.paramMap.get('id')), ratings:[this.rating], reviews:[this.comment]};
      this.reviewService.createReviewObject(newReview).subscribe();
      this.message = "success";
      this.router.navigate(["/buyer-product-list"]);
    }
  }
}
