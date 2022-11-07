import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AddMovie } from '../Model/addMovie.model';
import { Database } from '../Model/database.model';
@Injectable({
  providedIn: 'root',
})
export class EmployeeService {
  private baseUrl = environment.baseUrl;
  constructor(private http: HttpClient) {}

  getDatabaseMetadata() {
    return this.http.get<Database>(`${this.baseUrl}/database`);
  }

  addMovie(addMovie: AddMovie) {
    return this.http.post(`${this.baseUrl}/database`, addMovie);
  }
}
