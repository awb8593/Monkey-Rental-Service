import { Injectable } from '@angular/core';
import { Monkey } from './monkey';

import { MonkeyService } from './monkey.service';

import { CurrentUserService } from './current-user.service';

import { RentalService } from './rental.service';
import { Rental } from './rental';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {
  
  items: Monkey[] =[];
  
  constructor(
    private currentUserService: CurrentUserService,
    private monkeyService: MonkeyService,
    private rentalService: RentalService
    ) {}

  addToCart(monkey: Monkey){
    this.items.push(monkey);
    this.currentUserService.user.cartList.push(monkey.id)

    this.currentUserService.save();
  }

  deleteFromUser(index: number){
    
    this.currentUserService.user.cartList.splice(index, 1);
    
    this.currentUserService.save();
  }

  clearCart(){
    for(let i = 0; i < this.items.length; i ++) { // add each item to the user's rentals
      let monkeyid = this.items[i].id
      let tempDate = new Date();
      tempDate.setDate(tempDate.getDate() + 7);
      var rental = {
        id: 0,
        rentalDate: new Date().toJSON(),
        returnDate: tempDate.toJSON(),
        userId: this.currentUserService.user.id,
        monkeyId: monkeyid,
        active: true
      }
      this.rentalService.addRental(rental).subscribe();
    }
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