import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Customer } from 'src/app/Model/customer.model';
import { Login } from 'src/app/Model/login.model';
import { LoginService } from 'src/app/Services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  istablogin: boolean = true;
  istabregister: boolean = false;

  loginData: Login = new Login();
  registerData: Customer = new Customer();

  constructor(private loginService: LoginService, private router: Router) {}

  ngOnInit(): void {}

  tablogin() {
    this.istablogin = true;
    this.istabregister = false;
  }
  tabregister() {
    this.istablogin = false;
    this.istabregister = true;
  }

  login() {
    this.loginService.login(this.loginData).subscribe(
      (res) => {
        localStorage.setItem('user', this.loginData.email);
        this.router.navigateByUrl('/movie');
        console.log('Logged In');
      },
      (error) => {
        alert(error.error.errorMessage);
      }
    );
  }

  register() {
    this.loginService.register(this.registerData).subscribe(
      (res) => {
        alert('You have successfully Registered !! Now you can Login');
        this.tablogin();
        console.log('Registered');
      },
      (error) => {
        alert(error.error.errorMessage);
      }
    );
  }
}
