import { Component, Input, OnInit } from '@angular/core';

import { Monkey } from '../monkey';
import { MonkeyService } from '../monkey.service';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { ShoppingCartService } from '../shopping-cart.service';

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.css']
})
export class ProductPageComponent implements OnInit{
  monkey: Monkey | undefined;
  monkeys: Monkey[] = [];
  @Input() monkeyID: number = 0;

  constructor(
    private monkeyService: MonkeyService,
    private route: ActivatedRoute,
    private location: Location,
    private shoppingCartService: ShoppingCartService
  ) {}

  ngOnInit(): void {
    this.getMonkeys();
  }

  getMonkeys(): void {
    this.monkeyService.getMonkeys()
    .subscribe(monkeys => this.monkeys = monkeys);
  }

  addToCart(monkey: Monkey): void {
    monkey.rented = true;
    this.monkeyService.updateMonkey(monkey).subscribe();
    this.shoppingCartService.addToCart(monkey);
  }
}
