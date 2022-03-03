import { Component, OnInit } from '@angular/core';

import { Monkey } from '../monkey';
import { MonkeyService } from '../monkey.service';

@Component({
  selector: 'app-buyer-product-list',
  templateUrl: './buyer-product-list.component.html',
  styleUrls: ['./buyer-product-list.component.css']
})
export class BuyerProductListComponent implements OnInit {
  monkeys: Monkey[] = [];
  message:string = "";

  constructor(private monkeyService: MonkeyService) { }

  ngOnInit(): void {
    this.getMonkeys();
  }

  getMonkeys(): void {
    this.monkeyService.getMonkeys()
    .subscribe(monkeys => this.monkeys = monkeys);
  }

  clickEvent(monkey: Monkey): void {
    this.message = monkey.name + " has been clicked";
  }
}
