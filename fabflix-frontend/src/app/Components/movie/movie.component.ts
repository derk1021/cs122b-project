import { Component, OnInit } from '@angular/core';
import { Movie } from 'src/app/Model/movie.model';
import { MovieService } from 'src/app/Services/movie.service';

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css'],
})
export class MovieComponent implements OnInit {
  constructor(private movieService: MovieService) {}
  topRatedMovies!: Array<Movie>;
  searchString!:string;
  ngOnInit(): void {
    this.fetchMovies();
  }

  fetchMovies() {
    this.movieService
      .findTopRatedMovies()
      .subscribe((res: Movie[]) => (this.topRatedMovies = res));
  }

  findMovieByGenre(){
    if(this.searchString !='' || this.searchString != undefined){
    this.movieService.findMovieByGenre(this.searchString).subscribe((res)=>{
      this.topRatedMovies=res;
    })
  }
  }
  findMovieByName(){
    if(this.searchString !='' || this.searchString != undefined){
    this.movieService.findMovieByName(this.searchString).subscribe((res)=>{
      this.topRatedMovies=res;
    })
  }
  }
}
