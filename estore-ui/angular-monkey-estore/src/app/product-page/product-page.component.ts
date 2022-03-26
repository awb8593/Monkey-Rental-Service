import { Component, OnInit } from '@angular/core';

import { Monkey } from '../monkey';
import { MonkeyService } from '../monkey.service';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { ShoppingCartService } from '../shopping-cart.service';
import { UserService } from '../user.service';
import { CurrentUserService } from '../current-user.service';

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.css']
})
export class ProductPageComponent implements OnInit{
  monkey: Monkey | undefined;
  cartMonkeys: Monkey[] = [];

  constructor(
    private monkeyService: MonkeyService,
    private route: ActivatedRoute,
    private location: Location,
    private shoppingCartService: ShoppingCartService,
    private userService: UserService,
    private currentUserService: CurrentUserService
  ) {}

  ngOnInit(): void {
    this.getMonkey();
    this.getCartMonkeys();
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
}