import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './Components/login/login.component';
import { MovieDetailComponent } from './Components/movie-detail/movie-detail.component';
import { MovieComponent } from './Components/movie/movie.component';
import { AuthGuardService as AuthGuard } from './Services/auth-guard.service';

const routes: Routes = [
  // {
  //   path: '',
  //   // component: LoginComponent,
  //   // redirectTo: 'movie',
  // },
  {
    path: 'movie',
    component: MovieComponent,
    // canActivate: [AuthGuard],
  },
  {
    path: 'movie/detail/:id',
    component: MovieDetailComponent,
    // canActivate: [AuthGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
