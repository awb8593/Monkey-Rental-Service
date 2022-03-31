import { Component, OnInit } from '@angular/core';

import { Monkey } from '../monkey';
import { MonkeyService } from '../monkey.service';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { ShoppingCartService } from '../shopping-cart.service';
import { UserService } from '../user.service';
import { CurrentUserService } from '../current-user.service';
import { ReviewService } from '../review.service';
import { Review } from '../review';

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.css']
})
export class ProductPageComponent implements OnInit{
  monkey: Monkey | undefined;
  cartMonkeys: Monkey[] = [];
  review?: Review;

  constructor(
    private monkeyService: MonkeyService,
    private route: ActivatedRoute,
    private location: Location,
    private shoppingCartService: ShoppingCartService,
    private userService: UserService,
    private currentUserService: CurrentUserService,
    private reviewService: ReviewService
  ) {}

  ngOnInit(): void {
    this.getMonkey();
    this.getCartMonkeys();
    this.getReviewObject();
  }

  getMonkey(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.monkeyService.getMonkey(id)
    .subscribe(monkey => this.monkey = monkey);
  }

  goBack(): void {
    this.location.back();
  }

  addToCart(monkey: Monkey): void {
    this.getCartMonkeys();
    if (this.cartMonkeys.includes(monkey)){}
    else this.shoppingCartService.addToCart(monkey);
    setTimeout(()=>{this.getCartMonkeys();},100)
  }

  getCartMonkeys(): void {
    this.userService.getUserCart(this.currentUserService.user.id)
    .subscribe(monkeys => this.cartMonkeys = monkeys);
  }

  checkCart(monkey: Monkey): boolean {
    for (let m of this.cartMonkeys){
      if (m.id == monkey.id)
        return true;
    }
    return false;
  }

  getReviewObject(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.reviewService.getReviewObject(id)
    .subscribe(review => this.review = review);
  }

  getReviews(): String[] {
    if (this.review != undefined){
      return this.review?.reviews;
    }
    else {
      return [];
    }
  }

  getAverageRating(): number {
    if (this.review != undefined){
      let numArray: number[] = this.review.ratings;
      let total = 0;
      for (let i=0; i < numArray.length; i++){
        total += numArray[i];
      }
      return Number((total/numArray.length).toFixed(2));
    }
    else return -1;
  }
}