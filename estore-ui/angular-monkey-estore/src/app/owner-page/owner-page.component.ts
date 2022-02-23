import { Component, OnInit } from '@angular/core';
import { Monkey } from '../monkey';
import { MONKEYS } from './mock-monkey';

@Component({
  selector: 'app-owner-page',
  templateUrl: './owner-page.component.html',
  styleUrls: ['./owner-page.component.css']
})
export class OwnerPageComponent implements OnInit {

  monkeys = MONKEYS;
  selectedMonkey?: Monkey;

  constructor() { }

  ngOnInit(): void {
  }

  onSelect(monkey: Monkey): void {
    this.selectedMonkey = monkey;
  }
}
