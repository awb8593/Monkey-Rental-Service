import { Component, OnInit } from '@angular/core';
import { ReviewService } from '../review.service';
import { Monkey } from '../monkey';
import { MonkeyService } from '../monkey.service';
import { Location } from '@angular/common';
import { Review } from ''

@Component({
  selector: 'app-rental-page',
  templateUrl: './rental-page.component.html',
  styleUrls: ['./rental-page.component.css']
})
export class RentalPageComponent implements OnInit {

  constructor(private reviewService: ReviewService) { }

  ngOnInit(): void {
  }

}
