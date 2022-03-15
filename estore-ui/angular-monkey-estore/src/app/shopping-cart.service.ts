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

 // deleteFromCart(index: number){  
 //   this.currentUserService.load();
 //   this.currentUserService.user.cartList.splice(index, 1);
 //   this.currentUserService.save();
 // }

  deleteFromCart(id: number){
    this.currentUserService.load();
    for (let k = 0; k < this.currentUserService.user.cartList.length; k++){
      if (this.currentUserService.user.cartList[k] == id){
        this.currentUserService.user.cartList.splice(k, 1);
        break;
      }
    }
    this.currentUserService.save();
  }

  clearCart(){
    this.currentUserService.user.cartList = [];
    this.currentUserService.save();
  }



}
