import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Movie } from 'src/app/Model/movie.model';
import { MovieService } from 'src/app/Services/movie.service';

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css'],
})
export class MovieComponent implements OnInit {
  constructor(private movieService: MovieService,private route: ActivatedRoute,private router: Router) {}
  topRatedMovies!: Array<Movie>;
  searchString!:string;
  genreName!:string;
  titleStartsWith!:string;
  title!:string;
  director!:string;
  year!:number;
  star!:string;
  public page: number = 1;
  public totalLength: number = 0;
  ngOnInit(): void {
    let oldSearch = sessionStorage.getItem('searchResult')
    if(oldSearch!==null){
      oldSearch = JSON.parse(oldSearch)
    }
    this.topRatedMovies = history.state.movies? history.state.movies : oldSearch
    this.route.queryParams.subscribe(params=>{
      this.genreName = params['genre'];
      this.titleStartsWith = params['titleStartsWith'];
    })
    if(this.genreName){
      this.findMovieByGenre();
    }else if (this.titleStartsWith){
      this.findMovieByName();
    }
  }

  findMovieByGenre(){
    this.movieService.findMovieByGenre(this.genreName).subscribe((data:any)=>{
      this.topRatedMovies=data
      sessionStorage.setItem('searchResult',JSON.stringify(data))
  })
  }
  findMovieByName(){
    this.movieService.findMovieByName(this.titleStartsWith).subscribe((data:any)=>{
      this.topRatedMovies=data
      sessionStorage.setItem('searchResult',JSON.stringify(data))
    })
  }
}
