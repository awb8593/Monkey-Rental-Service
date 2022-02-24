import { Component, OnInit } from '@angular/core';
import { Monkey } from '../monkey';
import { MONKEYS } from './mock-monkey';
import { MonkeyService } from '../monkey.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-owner-page',
  templateUrl: './owner-page.component.html',
  styleUrls: ['./owner-page.component.css']
})
export class OwnerPageComponent implements OnInit {
  
  ngOnInit(): void {
    this.getMonkeys();
  }

  constructor(
    private monkeyService: MonkeyService,
    private location: Location
  ) { }

  onSelect(monkey: Monkey): void {
    this.selectedMonkey = monkey;
  }

  monkey: Monkey | undefined;

  monkeys: Monkey[] = [];
  selectedMonkey?: Monkey;

  getMonkeys(): void {
    this.monkeyService.getMonkeys()
    .subscribe(monkeys => this.monkeys = monkeys);
  }

  add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    this.monkeyService.addMonkey({ name } as Monkey)
      .subscribe(monkey => {
        this.monkeys.push(monkey);
      });
  }

  delete(monkey: Monkey): void {
    this.monkeys = this.monkeys.filter(m => m !== monkey);
    this.monkeyService.deleteMonkey(monkey.id).subscribe();
  }

  goBack(): void {
    this.location.back();
  }

  update(monkey: Monkey): void {
    this.monkeyService.updateMonkey(monkey).subscribe(monkey => {this.monkeys.push(monkey);
    });
  }
}
