import { Component } from '@angular/core';
import { CurrentUserService } from './current-user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Monkey e-Store';
  
  
  constructor(
    public currentUserService: CurrentUserService,
    
  ) {}
}

