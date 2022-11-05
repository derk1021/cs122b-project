import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Movie } from '../Model/movie.model';
import { Star } from '../Model/star.model';

@Injectable({
  providedIn: 'root',
})
export class StarService {
  private baseUrl = '  http://localhost:8080/api';
  constructor(private http: HttpClient) {}

  findMoviesByStarId(starId: string) {
    return this.http.get<Movie[]>(`${this.baseUrl}/star/${starId}/movie`);
  }

  findStarById(starId: string) {
    return this.http.get<Star>(`${this.baseUrl}/star/${starId}`);
  }
}
