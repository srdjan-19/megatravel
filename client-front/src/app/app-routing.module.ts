import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SignUpComponent } from './auth/sign-up/sign-up.component';
import { SignInComponent } from './auth/sign-in/sign-in.component';
import { AccommodationComponent } from './components/accommodation/accommodation.component';
import { AccountComponent } from './components/account/account.component';
import { InboxComponent } from './components/inbox/inbox.component';
import { HomeComponent } from './components/home/home.component';
import { PageNotFoundComponent } from './shared/page-not-found/page-not-found.component';
import { AuthGuard } from '../app/auth/guards/auth-guard.service';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full',
  },
  {
    path: '',
    children: [
      {
        path: 'home',
        component: HomeComponent
      },
      {
        path: 'signup',
        component: SignUpComponent
      },
      {
        path: 'signin',
        component: SignInComponent
      },
      {
        path: 'accommodations',
        component: AccommodationComponent
      },
      {
        path: 'account',
        component: AccountComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'inbox',
        component: InboxComponent,
        canActivate: [AuthGuard],
      },
    ]
  },
  {
    path: '**',
    component: PageNotFoundComponent
  },
  {
    path: '404',
    component: PageNotFoundComponent
  }

];

export const routing = RouterModule.forRoot(routes, { useHash: true });

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
