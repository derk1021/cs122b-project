import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MovieComponent } from './Components/movie/movie.component';
import { LoginComponent } from './Components/login/login.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MovieDetailComponent } from './Components/movie-detail/movie-detail.component';
import { NavbarComponent } from './Components/navbar/navbar.component';
import { StarDetailComponent } from './Components/star-detail/star-detail.component';
import {
  RECAPTCHA_SETTINGS,
  RecaptchaFormsModule,
  RecaptchaModule,
  RecaptchaSettings,
} from 'ng-recaptcha';
import { DashboardComponent } from './Components/dashboard/dashboard.component';
import { AddMovieComponent } from './Components/add-movie/add-movie.component';
import { AddStarComponent } from './Components/add-star/add-star.component';
import { DatabaseComponent } from './Components/database/database.component';
import { TableDescriptionComponent } from './Components/table-description/table-description.component';

@NgModule({
  declarations: [
    AppComponent,
    MovieComponent,
    LoginComponent,
    MovieDetailComponent,
    NavbarComponent,
    StarDetailComponent,
    DashboardComponent,
    AddMovieComponent,
    AddStarComponent,
    DatabaseComponent,
    TableDescriptionComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NoopAnimationsModule,
    RecaptchaModule,
    RecaptchaFormsModule,
  ],
  providers: [
    {
      provide: RECAPTCHA_SETTINGS,
      useValue: { siteKey: '6LeIwOAiAAAAADskWVITh7pT0zuA9L9Sb6_b52CA' } as RecaptchaSettings,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
