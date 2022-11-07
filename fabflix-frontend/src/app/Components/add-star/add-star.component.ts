import { Component, OnInit } from '@angular/core';
import { Star } from 'src/app/Model/star.model';
import { StarService } from 'src/app/Services/star.service';

@Component({
  selector: 'app-add-star',
  templateUrl: './add-star.component.html',
  styleUrls: ['./add-star.component.css'],
})
export class AddStarComponent implements OnInit {
  constructor(private starService: StarService) {}

  star: Star = new Star();

  ngOnInit(): void {}

  addStar() {
    console.log(this.star);
    this.starService.addNewStar(this.star).subscribe(
      (res) => {
        alert('New Star Added Successfully');
      },
      (error) => {
        console.log(error);
        alert(error.error.errors);
      }
    );
  }
}
