import { NONE_TYPE } from '@angular/compiler';
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
  selectedMonkey?: Monkey;
  monkeyID: number = 0;

  constructor(private monkeyService: MonkeyService) { }

  ngOnInit(): void {
    this.getMonkeys();
  }

  getMonkeys(): void {
    this.monkeyService.getMonkeys()
    .subscribe(monkeys => this.monkeys = monkeys);
  }

  clickEvent(monkey: Monkey): void {
    this.selectedMonkey = monkey;
    this.monkeyID = monkey.id;
  }
}
