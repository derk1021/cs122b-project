import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Login } from '../Model/login.model';
import { Movie } from '../Model/movie.model';
@Injectable({
  providedIn: 'root',
})
export class MovieService {
  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) {}

  findTopRatedMovies() {
    return this.http.get<Movie[]>(`${this.baseUrl}/movie`);
  }

  findMovieById(movieId: string) {
    return this.http.get<Movie>(`${this.baseUrl}/movie/${movieId}`);
  }

  findMovieByName(movieName: string) {
    return this.http.get<Movie[]>(`${this.baseUrl}/movie/title/${movieName}`);
  }

  findMovieByGenre(genreName: string) {
    return this.http.get<Movie[]>(`${this.baseUrl}/movie/genre/${genreName}`);
  }
}
