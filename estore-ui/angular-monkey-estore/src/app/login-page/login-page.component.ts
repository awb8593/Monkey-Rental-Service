import { Component } from '@angular/core';

import { User } from '../user';
import { UserService } from '../user.service';

import { CurrentUserService } from '../current-user.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent{

  constructor(
    public currentUserService: CurrentUserService,
    private userService: UserService
  ) { 
    this.enterUsernameText = "Enter Username: ";
    this.createUsernameText = "Enter Username: ";
  }

  public enterUsernameText: string 
  public createUsernameText: string


  login(username: string): void {
    this.userService.getUserByName(username).subscribe(user => this.currentUserService.user = user);

    if(this.currentUserService.user.id == 0) {
      this.enterUsernameText = "Invalid Username, Please Try Again: ";
    }
  }

  logout(): void{
    this.currentUserService.resetDefault();
    this.enterUsernameText = "Enter Username: ";
    this.createUsernameText = "Enter Username: ";
  }

  newUser: User = {id:0, username:"", cartList:[], rentedList:[]};
  createUser(newUsername: string): void {
    this.newUser.username = newUsername;
    this.userService.addUser(this.newUser).subscribe(user => this.currentUserService.user = user);

    if(this.currentUserService.user.id == 0) {
      this.createUsernameText = "Invalid Username, Please Try Again: ";
    }
  }

}
