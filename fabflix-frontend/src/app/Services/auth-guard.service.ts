import { Injectable } from '@angular/core';
import {
  CanActivate,
  Router,
} from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthGuardService implements CanActivate {
  constructor(public router: Router) {}
  canActivate(): boolean {
    const email = localStorage.getItem('user');
    if (email == '' || email == null) {
      this.router.navigate(['']);
      return false;
    }
    return true;
  }
}
