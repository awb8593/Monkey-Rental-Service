import { Injectable } from '@angular/core';

import { User } from './user';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class CurrentUserService {

  // Default Layer
  user: User = {id:0, username:"default", cartList:[], rentedList:[]};

  constructor(
    private userService:  UserService
  ) {}

  save(): void {
    if(this.user.id != 0) {
      this.userService.updateUser(this.user).subscribe(user => this.user = user);
    }
  }

  load(): void {
    if(this.user.id != 0) {
      this.userService.getUserById(this.user.id).subscribe(user => this.user = user);
    }
  }

  resetDefault(): void {
    this.user = {id:0, username:"default", cartList:[], rentedList:[]};
  }
}
