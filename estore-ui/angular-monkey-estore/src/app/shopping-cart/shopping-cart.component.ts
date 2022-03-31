import { Component, OnInit } from '@angular/core';
import { CurrentUserService } from '../current-user.service';
import { Monkey } from '../monkey';
import { ShoppingCartService } from '../shopping-cart.service';
import { UserService } from '../user.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  items : Monkey[] = [];

  constructor(private cartService: ShoppingCartService,
    public currentUserService: CurrentUserService,
    private userService: UserService) { }

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
    setTimeout(()=>{this.getMonkeys();},100);
  }

  ngOnInit(): void {
    this.getMonkeys();
  }

  getMonkeys(): void{
    this.userService.getUserCart(this.currentUserService.user.id)
    .subscribe(cartArray => this.items = cartArray);
  }

  checkCart(monkey: Monkey): boolean {
    for (let m of this.items){
      if (m.id == monkey.id)
        return true;
    }
    return false;
  }
}