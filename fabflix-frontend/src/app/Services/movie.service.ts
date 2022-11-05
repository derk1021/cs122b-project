import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Login } from '../Model/login.model';
import { Movie } from '../Model/movie.model';

@Injectable({
  providedIn: 'root',
})
export class MovieService {
  // private baseUrl = '  http://localhost:8080/api';
  private baseUrl = '  http://43.206.8.34:8080/backend/api';

  constructor(private http: HttpClient) {}

  findTopRatedMovies() {
    return this.http.get<Movie[]>(`${this.baseUrl}/movie`);
  }

  findMovieById(movieId: string) {
    return this.http.get<Movie>(`${this.baseUrl}/movie/${movieId}`);
  }
}
