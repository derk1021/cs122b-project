import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Login } from '../Model/login.model';
import { Customer } from '../Model/customer.model';
import { Employee } from '../Model/employee.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private baseUrl = environment.baseUrl;
  constructor(private http: HttpClient) {}
  login(login: Login) {
    console.log(login);
    return this.http.post(`${this.baseUrl}/login`, login);
  }

  loginEmployee(login: Employee) {
    console.log(login);
    return this.http.post(`${this.baseUrl}/login/employee`, login);
  }

  register(customer: Customer) {
    console.log(customer);
    return this.http.post(`${this.baseUrl}/register`, customer);
  }

  isEmployeeLoggedIn() {
    return localStorage.getItem('employee') != null;
  }
  logout() {
    localStorage.removeItem('user');
    localStorage.removeItem('employee');
  }
}
