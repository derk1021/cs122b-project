import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable} from 'rxjs';
import { Genre } from 'src/app/Model/genre.model';
import { Movie } from 'src/app/Model/movie.model';
import { GenreService } from 'src/app/Services/genre.service';
import { MovieService } from 'src/app/Services/movie.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  searchText!: string;
  genreList!:Genre[]
  titleList:string[] =['A','B','C','D','E','F','G','H','I','J','K','L','M','N','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z']
  year!:number;
  title!:string;
  director!:string;
  starName!:string;
  movies:any=[{ title: "Mansi"},{title : "Good , Bad Ugly"},{title : "Good Ugly"}];

  searchedMovies!:Observable<Movie[]>; 
  filteredList:Movie[]=[];
  listHidden = true;
  selectedIndex = -1;
  selectedSuggestion=false

  delay(ms: number) {
    return new Promise((resolve) => setTimeout(resolve, ms));
  }

  constructor(private genreService : GenreService,private router:Router,private movieService:MovieService) { 

  }

  ngOnInit(): void {
    this.getAllGenres(); 
    this.selectedSuggestion=false;    
  }

  async getFilteredList(event: string) {
    await this.delay(400);
    if ( event==this.searchText && event && event.length > 2) { 
      this.listHidden = false;
      console.log('waiting for delay')
      
      console.log('delay complete calling AutoSearch')
      let cachedResult = localStorage.getItem(this.searchText);
      if(!cachedResult){
        console.log("Getting Data from backend for search : " +this.searchText)
        this.movieService.findMovieByCriteria(this.searchText,0,'null','null').subscribe((res)=>{
        this.filteredList = res.slice(0,10);
        console.log(res.slice(0,10));
        localStorage.setItem(this.searchText,JSON.stringify(res.slice(0,10)));
        sessionStorage.setItem('searchResult',JSON.stringify(this.filteredList))
      }) } else{
        console.log("Getting Data from cache for search : " +this.searchText +" Using CacheResult")
        this.filteredList = JSON.parse(cachedResult);
        console.log(JSON.parse(cachedResult));
        sessionStorage.setItem('searchResult',JSON.stringify(this.filteredList))
      }
    
    }
  }

  selectItem(ind: number) {
    this.searchText = this.filteredList[ind].title;
    this.selectedIndex = ind;
  }

  onKeyPress(event:any) {
    if(!this.listHidden){
      if (event.key === 'Escape') {
        this.selectedIndex = -1;
        this.listHidden = true;
        this.selectedSuggestion=false;
        this.filteredList=[];
        this.searchText=""
        
      }
      if (event.key === 'Backspace') {
        this.selectedIndex = -1;
        this.listHidden = true;
        this.selectedSuggestion=false;
        this.filteredList=[]
        
      }
      if (event.key === 'ArrowDown') {
        this.selectedIndex = (this.selectedIndex + 1) % this.filteredList.length;
        if (this.filteredList.length > 0 && !this.listHidden) {
          this.selectedSuggestion=true;
          this.searchText =document.getElementsByClassName('dropdown-item')[this.selectedIndex].innerHTML;
          document.getElementsByTagName('li')[this.selectedIndex].scrollIntoView();
        }
      } else if (event.key === 'ArrowUp') {
        if (this.selectedIndex <= 0) {
          this.selectedIndex = this.filteredList.length;
        }
        this.selectedIndex = (this.selectedIndex - 1) % this.filteredList.length;

        if (this.filteredList.length > 0 && !this.listHidden) {
          this.selectedSuggestion=true;
          this.searchText =document.getElementsByClassName('dropdown-item')[this.selectedIndex].innerHTML;
          document.getElementsByTagName('li')[this.selectedIndex].scrollIntoView();
        }
      }
    } 
  }

  getAllGenres(){
    this.genreService.findAllGenres().subscribe((res)=>{
      this.genreList=res;
    })
  }

  searchByCriteria(){
    if(!this.title){
      this.title = 'null'
    }
    if(!this.year){
      this.year = 0
    }
    if(!this.director){
      this.director = 'null'
    }
    if(!this.starName){
      this.starName = 'null'
    }
    this.movieService.findMovieByCriteria(this.title,this.year,this.director,this.starName).subscribe((res:any)=>{
      this.searchedMovies=res
      sessionStorage.setItem('searchResult',JSON.stringify(this.searchedMovies))
      this.router.navigateByUrl('/movie',{state: {'movies' :this.searchedMovies}})
    })
  }

  searchMovies(){
    sessionStorage.setItem('searchResult',JSON.stringify(this.filteredList))
    if(!this.selectedSuggestion){
      this.movieService.findMovieByCriteria(this.searchText,0,'null','null').subscribe((res:any)=>{
        this.searchedMovies=res
        this.router.navigateByUrl('/movie',{state: {'movies' :this.searchedMovies}})
      })
    }else{
      this.router.navigate(['/movie/detail/'+this.filteredList[this.selectedIndex].id])
    }   
  }
}
