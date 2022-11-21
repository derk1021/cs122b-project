import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Movie } from 'src/app/Model/movie.model';
import { Star } from 'src/app/Model/star.model';
import { StarService } from 'src/app/Services/star.service';

@Component({
  selector: 'app-star-detail',
  templateUrl: './star-detail.component.html',
  styleUrls: ['./star-detail.component.css'],
})
export class StarDetailComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private starService: StarService
  ) {}
  starId = '';
  starDetails!: Star;
  movies!: Movie[];
  ngOnInit(): void {
    this.starId = this.route.snapshot.params['id'];
    this.fetchStarDetails();
    this.fetchMovies();
  }
  fetchStarDetails() {
    this.starService.findStarById(this.starId).subscribe((res) => {
      this.starDetails = res;
    });
  }

  fetchMovies() {
    this.starService.findMoviesByStarId(this.starId).subscribe((res) => {
      this.movies = res;
    });
  }
}
