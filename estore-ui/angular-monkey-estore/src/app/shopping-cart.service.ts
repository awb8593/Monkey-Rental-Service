import { Injectable } from '@angular/core';
import { Monkey } from './monkey';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {
  
  items: Monkey[] =[];
  
  constructor() {}

  addToCart(monkey: Monkey){
    this.items.push(monkey);
    monkey.rented=true;
  }

  clearCart(){
    this.items = [];
    return this.items;
  }

  getMonkeys(){
    return this.items;
  }

}
