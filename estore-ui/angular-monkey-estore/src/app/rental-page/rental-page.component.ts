import { Component, OnInit } from '@angular/core';
import { ReviewService } from '../review.service';
import { UserService } from '../user.service';
import { CurrentUserService } from '../current-user.service';
import { Review } from '../review';
import { Rental } from '../rental'
import { Location } from '@angular/common';
import { MonkeyService } from '../monkey.service';
import { ShoppingCartService } from '../shopping-cart.service';
import { Monkey } from '../monkey';
import { RentalService } from '../rental.service';

@Component({
  selector: 'app-rental-page',
  templateUrl: './rental-page.component.html',
  styleUrls: ['./rental-page.component.css']
})
export class RentalPageComponent implements OnInit {

  constructor(private cartService: ShoppingCartService,
    public currentUserService: CurrentUserService,
    public monkeyService: MonkeyService,
    public rentalService: RentalService) 
    {}

  items: Rental[] = [];

  ngOnInit(): void {
    this.getRentedList();
  }

  getRentedList(){
    this.currentUserService.load();
    this.items = [];
    for (let k = 0; k < this.currentUserService.user.rentedList.length; k++){
      this.rentalService.getRentalsUser(this.currentUserService.user.id, true).subscribe(rentals => this.items = rentals)
    }
  }

  returnFromRented(item: Rental): void{
    for (let k = 0; k < this.items.length; k++){
      if (this.items[k].id == item.id){
        this.items[k].active = false
        this.rentalService.updateRental(this.items[k]).subscribe();;
        this.rentalService.deleteRental(this.items[k].id);
        break;
      }
    }
    this.getRentedList();
  }
}
