import { Injectable } from '@angular/core';
import { Monkey } from './monkey';

import { MonkeyService } from './monkey.service';

import { CurrentUserService } from './current-user.service';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {
  
  items: Monkey[] =[];
  
  constructor(
    private currentUserService: CurrentUserService,
    private monkeyService: MonkeyService
    ) {}

  addToCart(monkey: Monkey){
    this.items.push(monkey);
    this.currentUserService.user.cartList.push(monkey.id)
    monkey.rented=true;

    this.currentUserService.save();
  }

  deleteFromUser(index: number){
    
    this.currentUserService.user.cartList.splice(index, 1);
    
    this.currentUserService.save();
  }

  clearCart(){
    this.items = [];
    this.currentUserService.user.cartList = [];
    this.currentUserService.save();
    return this.items;
  }

  getMonkeys(){
    this.currentUserService.load();
    this.items = [];
    for (let k = 0; k < this.currentUserService.user.cartList.length; k++){
      this.monkeyService.getMonkey(this.currentUserService.user.cartList[k]).subscribe(monkey => this.items[k])
    }
    return this.items;
  }

}
