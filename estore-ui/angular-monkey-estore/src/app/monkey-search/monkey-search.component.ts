/**
 * Author: Jack Hester
 * Note: Must check any comment "!!!"
 */

import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import {
    debounceTime, distinctUntilChanged, switchMap
  } from 'rxjs/operators'

import { Monkey } from '../monkey'; // !!!
import { MonkeyService } from '../monkey.service'; // !!!

@Component({
  selector: 'app-monkey-search',
  templateUrl: './monkey-search.component.html',
  styleUrls: ['./monkey-search.component.css']
})
export class MonkeySearchComponent implements OnInit {

  monkeys$!: Observable<Monkey[]>;
  private searchTerms = new Subject<string>();

  constructor(private monkeyService: MonkeyService) { } // !!!

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.monkeys$ = this.searchTerms.pipe( // !!!
      // wait 300 ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous teerm
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) =>
      this.monkeyService.searchMonkeys(term)),
    );
  }

}
