import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, shareReplay } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Genre } from '../Model/genre.model';

@Injectable({
  providedIn: 'root'
})
export class GenreService {
  private baseUrl = environment.baseUrl;

  private genres$!: Observable<Genre[]>;
  constructor(private http: HttpClient) {}

  findAllGenres() {
    if(!this.genres$){ 
      this.genres$ = this.http.get<Genre[]>(`${this.baseUrl}/genre`).pipe(shareReplay(1))
      console.log('Returning Genres from backend')
    }else{
      console.log('Returning Genres from cache')
    }
    return this.genres$;
  }
}
