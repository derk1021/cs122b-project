import { Component, OnInit } from '@angular/core';
import { AddMovie } from 'src/app/Model/addMovie.model';
import { EmployeeService } from 'src/app/Services/employee.service';

@Component({
  selector: 'app-add-movie',
  templateUrl: './add-movie.component.html',
  styleUrls: ['./add-movie.component.css'],
})
export class AddMovieComponent implements OnInit {
  movie: AddMovie = new AddMovie();
  constructor(private employeeService: EmployeeService) {}

  ngOnInit(): void {}

  addMovie() {
    this.employeeService.addMovie(this.movie).subscribe(
      (res) => {
        alert(res);
      },
      (error) => {
        alert(error.error.erroMessage);
      }
    );
  }
}
