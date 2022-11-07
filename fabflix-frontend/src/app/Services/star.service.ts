import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Movie } from '../Model/movie.model';
import { Star } from '../Model/star.model';

@Injectable({
  providedIn: 'root',
})
export class StarService {
  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) {}

  findMoviesByStarId(starId: string) {
    return this.http.get<Movie[]>(`${this.baseUrl}/star/${starId}/movie`);
  }

  findStarById(starId: string) {
    return this.http.get<Star>(`${this.baseUrl}/star/${starId}`);
  }

  addNewStar(star: Star) {
    return this.http.post(`${this.baseUrl}/star/`, star);
  }
}
