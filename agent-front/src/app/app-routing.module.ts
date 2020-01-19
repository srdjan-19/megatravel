import { HomeComponent } from './components/home/home.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './auth/login/login.component'
import { AccommodationsComponent } from './components/accommodations/accommodations.component'
import { AuthGuard } from './auth/guards/auth-guard.service';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { InboxComponent } from './components/inbox/inbox.component';
import { ReservationComponent } from './components/reservation/reservation.component';

const routes: Routes = [
  {
    path: 'login', component: LoginComponent
  },
  {
    path: '', component: HomeComponent
  },
  {
    path: '', children: [
      {
        path: 'accommodations', component: AccommodationsComponent,
      },
      {
        path: 'inbox', component: InboxComponent, canActivate: [AuthGuard]
      },
      {
        path: 'reservations', component: ReservationComponent, canActivate: [AuthGuard]
      }
    ]
  },
  {
    path: '**',
    component: PageNotFoundComponent
  },
  {
    path: '404', component: PageNotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }