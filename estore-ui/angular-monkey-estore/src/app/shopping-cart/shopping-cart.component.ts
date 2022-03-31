import { Component, OnInit } from '@angular/core';
import { CurrentUserService } from '../current-user.service';
import { Monkey } from '../monkey';
import { ShoppingCartService } from '../shopping-cart.service';
import { UserService } from '../user.service';
import { MonkeyService } from '../monkey.service';
import { Location } from '@angular/common';


@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  items: Monkey[] = [];
  total: number = 0;

  constructor(private cartService: ShoppingCartService,
    public currentUserService: CurrentUserService,
    public monkeyService: MonkeyService,
    private location: Location,
    private userService: UserService) 
    {}

  getMonkeyList(){
    this.currentUserService.load();
    this.items = [];
    this.total = 0;
    for (let k = 0; k < this.currentUserService.user.cartList.length; k++){
      let monkey =  this.monkeyService.getMonkey(this.currentUserService.user.cartList[k]);
      monkey.subscribe(monkey => this.items.push(monkey));
      monkey.subscribe(monkey => this.total = (this.total + monkey.price));
    }
  }

  removeFromCart(item: Monkey): void{
    this.getMonkeys();
    for (let k = 0; k < this.items.length; k++){
      if (this.items[k].id == item.id){
        this.cartService.deleteFromUser(k);
        break;
      }
    }
    setTimeout(()=>{this.getMonkeys();},100);
  }

  clearCart(){
    this.cartService.clearCart();
  }

  ngOnInit(): void {
    this.getMonkeys();
  }

  getMonkeys(): void{
    this.userService.getUserCart(this.currentUserService.user.id)
    .subscribe(cartArray => this.items = cartArray);
  }

  goBack(): void {
    this.location.back();
  }

  checkCart(monkey: Monkey): boolean {
    for (let m of this.items){
      if (m.id == monkey.id)
        return true;
    }
    return false;
  }
}