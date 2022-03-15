import { Component, OnInit } from '@angular/core';
import { CurrentUserService } from '../current-user.service';
import { Monkey } from '../monkey';
import { ShoppingCartService } from '../shopping-cart.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  items = this.cartService.getMonkeys();

  constructor(private cartService: ShoppingCartService,
    public currentUserService: CurrentUserService) { }

  removeFromCart(item: Monkey, button: any): void{
    for (let k = 0; k < this.items.length; k++){
      if (this.items[k].id == item.id){
        this.cartService.deleteFromUser(k);
        item.rented=false;
        break;
      }
    }
    this.cartService.getMonkeys();
  }

  clearCart(){
    this.cartService.clearCart();
  }


  ngOnInit(): void {
  }

}
