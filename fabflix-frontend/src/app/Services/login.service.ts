import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Login } from '../Model/login.model';
import { Employee } from '../Model/employee.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private baseUrl = environment.baseUrl;
  constructor(private http: HttpClient) {}
  login(login: Login) {
    return this.http.post(`${this.baseUrl}/login`, login);
  }

  loginEmployee(login: Employee) {
    return this.http.post(`${this.baseUrl}/login/employee`, login);
  }

  isEmployeeLoggedIn() {
    return localStorage.getItem('employee') != null;
  }
  logout() {
    localStorage.removeItem('user');
    localStorage.removeItem('employee');
  }
}
