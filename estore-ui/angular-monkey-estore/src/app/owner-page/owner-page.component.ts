import { Component, OnInit } from '@angular/core';
import { MONKEYS } from './mock-monkey';

@Component({
  selector: 'app-owner-page',
  templateUrl: './owner-page.component.html',
  styleUrls: ['./owner-page.component.css']
})
export class OwnerPageComponent implements OnInit {

  monkeys = MONKEYS;

  constructor() { }

  ngOnInit(): void {
  }

}
