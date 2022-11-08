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
    
    this.starService.addNewStar(this.star).subscribe(
      (res) => {
        alert(`New Star Added Successfully with Star ID : ${res.id}`);
      },
      (error) => {
        if(error.error.errors != null || error.error.errors != undefined){
          alert(error.error.errors);
        }else{
          alert(error.error.message);
        }
      }
    );
  }
}
