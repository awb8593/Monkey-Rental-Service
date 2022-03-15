import { Component, OnInit } from '@angular/core';
import { CurrentUserService } from '../current-user.service';
import { Monkey } from '../monkey';
import { ShoppingCartService } from '../shopping-cart.service';

import { MonkeyService } from '../monkey.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  items: Monkey[] = [];

  constructor(private cartService: ShoppingCartService,
    public currentUserService: CurrentUserService,
    public monkeyService: MonkeyService) 
    {}

  getMonkeyList(){
    this.currentUserService.load();
    this.items = [];
    for (let k = 0; k < this.currentUserService.user.cartList.length; k++){
      this.monkeyService.getMonkey(this.currentUserService.user.cartList[k]).subscribe(monkey => this.items.push(monkey))
    }
  }

  removeFromCart(item: Monkey): void{
    for (let k = 0; k < this.items.length; k++){
      if (this.items[k].id == item.id){
        this.cartService.deleteFromCart(this.items[k].id);
        break;
      }
    }
    this.getMonkeyList();
  }

  clearCart(){
    this.cartService.clearCart();
    this.getMonkeyList();
  }

  ngOnInit(): void {
    this.getMonkeyList();
  }

}
