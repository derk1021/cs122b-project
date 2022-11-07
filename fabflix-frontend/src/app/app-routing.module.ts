import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddMovieComponent } from './Components/add-movie/add-movie.component';
import { AddStarComponent } from './Components/add-star/add-star.component';
import { DashboardComponent } from './Components/dashboard/dashboard.component';
import { DatabaseComponent } from './Components/database/database.component';
import { LoginComponent } from './Components/login/login.component';
import { MovieDetailComponent } from './Components/movie-detail/movie-detail.component';
import { MovieComponent } from './Components/movie/movie.component';
import { StarDetailComponent } from './Components/star-detail/star-detail.component';
import { AuthGuardService as AuthGuard } from './Services/auth-guard.service';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    component: LoginComponent,
    pathMatch: 'full',
  },
  {
    path: '_dashboard',
    component: DashboardComponent,
    pathMatch: 'full',
  },
  {
    path: 'movie',
    component: MovieComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'movie/detail/:id',
    component: MovieDetailComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'star/detail/:id',
    component: StarDetailComponent,
    canActivate: [AuthGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
