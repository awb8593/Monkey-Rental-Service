import { Component, OnInit } from '@angular/core';
import { ReviewService } from '../review.service';
import { UserService } from '../user.service';
import { CurrentUserService } from '../current-user.service';
import { Review } from '../review';
import { Location } from '@angular/common';
import { MonkeyService } from '../monkey.service';
import { ShoppingCartService } from '../shopping-cart.service';
import { Monkey } from '../monkey';

@Component({
  selector: 'app-rental-page',
  templateUrl: './rental-page.component.html',
  styleUrls: ['./rental-page.component.css']
})
export class RentalPageComponent implements OnInit {

  constructor(private cartService: ShoppingCartService,
    public currentUserService: CurrentUserService,
    public monkeyService: MonkeyService) 
    {}

  items: Monkey[] = [];

  ngOnInit(): void {
    this.getRentedList();
  }

  getRentedList(){
    this.currentUserService.load();
    this.items = [];
    for (let k = 0; k < this.currentUserService.user.cartList.length; k++){
      this.monkeyService.getMonkey(this.currentUserService.user.cartList[k]).subscribe(monkey => this.items.push(monkey))
    }
  }

  returnFromRented(item: Monkey): void{
    for (let k = 0; k < this.items.length; k++){
      if (this.items[k].id == item.id){
        item.rented = false;
        this.monkeyService.updateMonkey(item).subscribe();;
        this.cartService.deleteFromCart(this.items[k].id);
        break;
      }
    }
    this.getRentedList();
  }
}
