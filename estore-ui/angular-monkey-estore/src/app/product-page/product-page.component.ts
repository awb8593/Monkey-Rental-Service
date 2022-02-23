import { Component, OnInit } from '@angular/core';
import { MonkeyService } from '../monkey.service';

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.css']
})
export class ProductPageComponent implements OnInit {
  monkeys: Monkey[] = [];

  constructor(private monkeyService: MonkeyService) { }

  ngOnInit(): void {
    this.getMonkeys();
  }

  getMonkeys(): void {
    this.monkeys = this.monkeyService.getMonkeys();
  }

  

}
