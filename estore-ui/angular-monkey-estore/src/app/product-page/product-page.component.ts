import { Component, Input, OnInit } from '@angular/core';

import { Monkey } from '../monkey';
import { MonkeyService } from '../monkey.service';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.css']
})
export class ProductPageComponent implements OnInit{
  monkey: Monkey | undefined;
  @Input() monkeyID: number = 0;

  constructor(
    private monkeyService: MonkeyService,
    private route: ActivatedRoute,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getMonkey();
  }

  getMonkey(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.monkeyService.getMonkey(id)
    .subscribe(monkey => this.monkey = monkey);
  }

  goBack(): void {
    this.location.back();
  }

  addToCart(): void {
    
  }
}
