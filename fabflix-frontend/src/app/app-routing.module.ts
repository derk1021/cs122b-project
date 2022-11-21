import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './Components/dashboard/dashboard.component';
import { HomeComponent } from './Components/home/home.component';
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
  },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [AuthGuard],
  },
  {
    path: '_dashboard',
    component: DashboardComponent,
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
