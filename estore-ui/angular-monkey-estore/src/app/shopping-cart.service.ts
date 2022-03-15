import { Injectable } from '@angular/core';
import { Monkey } from './monkey';


import { CurrentUserService } from './current-user.service';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {
  
  constructor(
    private currentUserService: CurrentUserService
    ) {}

  addToCart(monkey: Monkey){
    this.currentUserService.load();
    this.currentUserService.user.cartList.push(monkey.id);
    this.currentUserService.save();
  }

  deleteFromCart(index: number){  
    this.currentUserService.load();
    this.currentUserService.user.cartList.splice(index, 1);
    this.currentUserService.save();
  }

  clearCart(){
    this.currentUserService.user.cartList = [];
    this.currentUserService.save();
  }



}
