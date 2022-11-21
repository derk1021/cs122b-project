import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, shareReplay } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Movie } from '../Model/movie.model';
@Injectable({
  providedIn: 'root',
})
export class MovieService {
  private baseUrl = environment.baseUrl;
  constructor(private http: HttpClient) {}

  findMovieById(movieId: string) {
    return this.http.get<Movie>(`${this.baseUrl}/movie/${movieId}`);
  }

  findMovieByName(movieName: string) {
    return this.http.get<Movie[]>(`${this.baseUrl}/movie/title/${movieName}`);
  }

  findMovieByGenre(genreName: string) {
    return this.http.get<Movie[]>(`${this.baseUrl}/movie/genre/${genreName}`);
  }

  findMovieByCriteria(title:string,year:number,director:string,star:string):Observable<Movie[]>{
    return this.http.get<Movie[]>(`${this.baseUrl}/movie?title=${title}&year=${year}&director=${director}&star=${star}`);
  }
}
