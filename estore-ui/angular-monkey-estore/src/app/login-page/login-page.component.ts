import { Component } from '@angular/core';

import { User } from '../user';
import { UserService } from '../user.service';

import { CurrentUserService } from '../current-user.service';
import { HttpEventType, HttpHeaders, HttpResponse, HttpStatusCode } from '@angular/common/http';
import { Observable, of } from 'rxjs';

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

    const defaultUser: User = {id:0, username:"default", cartList:[], rentedList:[]};
    var body:User|null = defaultUser;
    var status:number = 0;
    
    if(username == "")
    {
      this.enterUsernameText = "Please Enter a username: ";
      return
    }

    //this.userService.getUserByName(username).subscribe(user => this.currentUserService.user = user);
    this.userService.getUserByNameWithResponse(username).subscribe(resp => 
      {
        setTimeout(()=>{}, 100);
        if(resp.status == 404) // Not Found = 404
        {
          this.enterUsernameText = "Username does not exist, please try again: ";
          return
        }
        else if(resp.status == 500) // Internal Server Error = 500
        {
          this.enterUsernameText = "Internal Server Error: ";
          return
        }
        else // Accepted 
        {
          // Check if response body is null before assigning currentuserservice
          if( resp.body != null){
            of(resp.body).subscribe(body => this.currentUserService.user = body) 
          }
          return;
        }
    });
  
    // Default Response
    this.enterUsernameText = "No Server Response Recieved: "; 
    return;

  }

  logout(): void{
    this.currentUserService.resetDefault();
    this.enterUsernameText = "Enter Username: ";
    this.createUsernameText = "Enter Username: ";
  }

 
  createUser(newUsername: string): void {
    var newUser: User = {id:0, username:"", cartList:[], rentedList:[]};
    newUser.username = newUsername;
    const defaultUser: User = {id:0, username:"default", cartList:[], rentedList:[]};
    var body:User|null = defaultUser;
    var status:number = 0;
    
    if(newUsername == "")
    {
      this.createUsernameText = "Please Enter a username: ";
      return
    }

    //this.userService.getUserByName(username).subscribe(user => this.currentUserService.user = user);
    this.userService.addUserWithResponse(newUser).subscribe(resp => 
      {
        setTimeout(()=>{}, 100);
        if(resp.status == 409) // Conflict 409
        {
          this.createUsernameText = "Username already exists, please try again: ";
          return
        }
        else if(resp.status == 500) // Internal Server Error = 500
        {
          this.createUsernameText = "Internal Server Error: ";
          return
        }
        else // Accepted 
        {
          // Check if response body is null before assigning currentuserservice
          if( resp.body != null){
            of(resp.body).subscribe(body => this.currentUserService.user = body) 
          }
          return;
        }
    });

    // Default Response
    this.createUsernameText = "No Server Response Recieved: "; 
    return;
  }
  
}
