import { Component, OnInit } from '@angular/core';
import { Monkey } from '../monkey';
import { ShoppingCartService } from '../shopping-cart.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  items = this.cartService.getMonkeys();

  constructor(private cartService: ShoppingCartService) { }

  removeFromCart(item: Monkey, button: any): void{
    for (let k = 0; k < this.items.length; k++){
      if (this.items[k].id == item.id){
        delete this.items[k];
        button.parentElement.remove();
        item.rented=false;
        break;
      }
    }

  }

  clearCart(){
    this.items = [];
    return this.items;
  }


  ngOnInit(): void {
  }

}
