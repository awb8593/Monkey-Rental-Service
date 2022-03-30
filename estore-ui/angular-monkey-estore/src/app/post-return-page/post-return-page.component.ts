import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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

  constructor(
    private monkeyService : MonkeyService,
    private reviewService : ReviewService,
    private route: ActivatedRoute,
    private location: Location
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
}
