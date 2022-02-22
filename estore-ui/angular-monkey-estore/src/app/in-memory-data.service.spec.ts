import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Monkey } from './Monkey';

@Injectable({
  providedIn: 'root',
})
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const monkey = [
      {id: 1, name: 'Aldo', price: 10.5, species: 'Baboon', description: 'Small monkey brought in 5 days ago', rented: false},
      {id: 2, name: 'Rafiki', price: 20.5, species: 'Mandrill', description: 'Found on set of lion king', rented: false}]
    ];
    return {monkeys};
  }

  // Overrides the genId method to ensure that a monkey always has an id.
  // If the heroes array is empty,
  // the method below returns the initial number (11).
  // if the monkeys array is not empty, the method below returns the highest
  // hero id + 1.
  genId(monkeys: Monkey[]): number {
    return monkeys.length > 0 ? Math.max(...monkeys.map(monkey => monkey.id)) + 1 : 11;
  }
}