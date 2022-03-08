import { Injectable } from '@angular/core';

import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class CurrentUserService {

  user: User | undefined;

  constructor() { }

  getUser(): User | undefined {
    return this.user;
  }

  setUser(user: User): void {
    this.user = user;
  }

}
