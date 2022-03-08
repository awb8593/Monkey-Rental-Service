import { Component, Input, OnInit } from '@angular/core';

import { Monkey } from '../monkey';
import { MonkeyService } from '../monkey.service';

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.css']
})
export class ProductPageComponent implements OnInit{
  monkeys: Monkey[] = [];
  @Input() monkeyID: number = 0;

  constructor(private monkeyService: MonkeyService) { }

  ngOnInit(): void {
    this.getMonkeys();
  }

  getMonkeys(): void {
    this.monkeyService.getMonkeys()
    .subscribe(monkeys => this.monkeys = monkeys);
  }
}
