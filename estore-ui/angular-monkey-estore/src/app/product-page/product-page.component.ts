import { Component, OnInit } from '@angular/core';
import { Monkey } from '../monkey';
import { MonkeyService } from '../monkey.service';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.css']
})
export class ProductPageComponent implements OnInit {
  monkeys: Monkey[] = [];
  selectedMonkey?: Monkey;

  constructor(
    private monkeyService: MonkeyService,
    private route: ActivatedRoute,
    private location: Location
    ) {}

  ngOnInit(): void {
    this.getMonkeys();
  }

  getMonkeys(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.monkeyService.getMonkeys()
    .subscribe(monkeys => this.monkeys = monkeys);
  }

  onSelect(monkey: Monkey): void {
    this.selectedMonkey = monkey;
  }

  

}
