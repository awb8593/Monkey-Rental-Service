import { Injectable } from '@angular/core';
import { Monkey } from './monkey';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {
  
  items: Monkey[] =[];
  /**
   * testMonkey is for testing purposes only
   */
   testMonkey: Monkey = {
    id: 34,
    name: 'Test Monkey',
    price: 12,
    species: 'Gorilla',
    description: "He's helping us out",
    rented: false
  }
  

  constructor() { 
    this.items.push(this.testMonkey) //For testing purposes only, this can be deleted

  }

  addToCart(monkey: Monkey){
    this.items.push(monkey);
  }

  clearCart(){
    this.items = [];
    return this.items;
  }

  getMonkeys(){
    return this.items;
  }

}
