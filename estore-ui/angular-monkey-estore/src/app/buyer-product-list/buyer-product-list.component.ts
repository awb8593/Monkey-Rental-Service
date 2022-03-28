import { NONE_TYPE } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import {
    debounceTime, distinctUntilChanged, switchMap
  } from 'rxjs/operators'
import { CurrentUserService } from '../current-user.service';

import { Monkey } from '../monkey';
import { MonkeyService } from '../monkey.service';
import { UserService } from '../user.service';

@Component({
  selector: 'app-buyer-product-list',
  templateUrl: './buyer-product-list.component.html',
  styleUrls: ['./buyer-product-list.component.css']
})
export class BuyerProductListComponent implements OnInit {
  monkeys$!:Observable<Monkey[]>;
  private searchTerms = new Subject<string>();
  monkeys: Monkey[] = [];
  selectedMonkey?: Monkey;
  monkeyID: number = 0;
  cartMonkeys : Monkey[] = [];

  constructor(private monkeyService: MonkeyService,
              private userService: UserService,
              private currentUserService: CurrentUserService) { }

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.getMonkeys();
    this.getCartMonkeys();
    this.monkeys$ = this.searchTerms.pipe( // !!!
      // wait 300 ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous teerm
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) =>
      this.monkeyService.searchMonkeys(term))
    );
  }

  getMonkeys(): void {
    this.monkeyService.getMonkeys()
    .subscribe(monkeys => this.monkeys = monkeys);
  }

  getCartMonkeys(): void {
    this.userService.getUserCart(this.currentUserService.user.id)
    .subscribe(monkeys => this.cartMonkeys = monkeys);
  }

  checkCart(monkey: Monkey): boolean {
    for (let m of this.cartMonkeys){
      if (m.id == monkey.id)
        return true;
    }
    return false;
  }

  clickEvent(monkey: Monkey): void {
    this.selectedMonkey = monkey;
    this.monkeyID = monkey.id;
  }
}