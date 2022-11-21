import { Component,OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Login } from 'src/app/Model/login.model';
import { LoginService } from 'src/app/Services/login.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {

  token: string|undefined;

  loginData: Login = new Login();

  constructor(private loginService: LoginService, private router: Router) {}

  ngOnInit(): void {}

  

  login(form: NgForm) {
    if (form.invalid) {
      for (const control of Object.keys(form.controls)) {
        form.controls[control].markAsTouched();
      }
      return;
    }
    this.loginService.login(this.loginData).subscribe(
      (res) => {
        localStorage.setItem('user', this.loginData.email);
        this.router.navigateByUrl('/home');
      },
      (error) => {
        alert(error.error.errorMessage);
      }
    );
  }
}
