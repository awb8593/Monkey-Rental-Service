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
  monkeys: Monkey[] = [];

  constructor(private cartService: ShoppingCartService,
    public currentUserService: CurrentUserService,
    public monkeyService: MonkeyService,
    public rentalService: RentalService) 
    {}

  items: Rental[] = [];

  selectedMonkey?: Monkey;

  onSelect(monkey: Monkey): void {
    this.selectedMonkey = monkey;
  }

  deselect(): void{
    this.selectedMonkey = undefined;
  }

  ngOnInit(): void {
    this.getRentedList();
    setTimeout(()=>{this.getMonkeyList();}, 100);
  }

  getRentedList(){
    this.currentUserService.load();
    this.rentalService.getRentalsUser(this.currentUserService.user.id, true).subscribe(rentals => this.items = rentals)
    setTimeout(()=>{this.getMonkeyList();}, 100);
  }

  getMonkeyList(){
    for (let k = 0; k < this.items.length; k++){
      this.monkeyService.getMonkey(this.items[k].monkeyId).subscribe(monkey => this.monkeys[k] = monkey)
    }
  }

  returnFromRented(id: number): void{
    for (let k = 0; k < this.items.length; k++){
      if (this.items[k].monkeyId == id){
        this.items[k].active = false
        this.rentalService.updateRental(this.items[k]).subscribe();;
        this.rentalService.deleteRental(this.items[k].id);
        break;
      }
    }
    this.getRentedList();
  }

  //getRentalMonkey(id: number): void{
    //this.monkeyService.getMonkey(id).subscribe(monkey => this.monkey = monkey)
  //}
}
