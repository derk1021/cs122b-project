import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Employee } from 'src/app/Model/employee.model';
import { LoginService } from 'src/app/Services/login.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  constructor(private loginService: LoginService) {this.token = undefined;}
  token: string|undefined;
  loginData: Employee = new Employee();
  isLoggedIn = false;
  addMovieTab = false;
  addStarPage = false;
  databaseTab = false;
  ngOnInit(): void {
    this.isLoggedIn = localStorage.getItem('employee') == null ? false : true;
  }
  showAddMovieTab() {
    this.addStarPage = false;
    this.addMovieTab = !this.addMovieTab;
    this.databaseTab = false;
  }
  showAddStarTab() {
    this.addStarPage = !this.addStarPage;
    this.addMovieTab = false;
    this.databaseTab = false;
  }
  showDatabaseTab() {
    this.addStarPage = false;
    this.addMovieTab = false;
    this.databaseTab = !this.databaseTab;
  }

  login(form:NgForm) {
    if (form.invalid) {
      for (const control of Object.keys(form.controls)) {
        form.controls[control].markAsTouched();
      }
      return;
    }
    this.loginService.loginEmployee(this.loginData).subscribe(
      (res) => {
        localStorage.setItem('employee', this.loginData.email);
        this.isLoggedIn = true;
        console.log('Logged In');
      },
      (error) => {
        alert(error.error.errorMessage);
      }
    );
  }
}
