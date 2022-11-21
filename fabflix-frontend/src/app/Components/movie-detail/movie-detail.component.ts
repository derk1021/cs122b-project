import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Movie } from 'src/app/Model/movie.model';
import { MovieService } from 'src/app/Services/movie.service';

@Component({
  selector: 'app-movie-detail',
  templateUrl: './movie-detail.component.html',
  styleUrls: ['./movie-detail.component.css'],
})
export class MovieDetailComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private movieService: MovieService,
  ) {}
  movieId = '';
  movieDetails!: Movie;
  ngOnInit(): void {
    this.movieId = this.route.snapshot.params['id'];
    this.fetchMovieDetails();
  }
  fetchMovieDetails() {
    this.movieService.findMovieById(this.movieId).subscribe((res) => {
      this.movieDetails = res;
    });
  }
}
