import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Login } from '../Model/login.model';
import { Customer } from '../Model/customer.model';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  // private baseUrl = '  http://localhost:8080/api';
  private baseUrl =
    'http://ec2-43-206-8-34.ap-northeast-1.compute.amazonaws.com:9090/api';
  constructor(private http: HttpClient) {}
  login(login: Login) {
    console.log(login);
    return this.http.post(`${this.baseUrl}/login`, login);
  }

  register(customer: Customer) {
    console.log(customer);
    return this.http.post(`${this.baseUrl}/register`, customer);
  }
}
