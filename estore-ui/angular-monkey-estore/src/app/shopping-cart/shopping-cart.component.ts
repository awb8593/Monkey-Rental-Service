import { Component, OnInit } from '@angular/core';
import { ShoppingCartService } from '../shopping-cart.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  items = this.cartService.getMonkeys();

  constructor(private cartService: ShoppingCartService) { }

  removeFromCart(id: any): void{
    for (let k = 0; k < this.items.length; k++){
      if (this.items[k].id == id){
        delete this.items[k];
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
