import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SignUpComponent } from './auth/sign-up/sign-up.component';
import { SignInComponent } from './auth/sign-in/sign-in.component';
import { AccommodationComponent} from './accommodation/accommodation.component';
import { AccountComponent } from './account/account.component';
import { InboxComponent } from './inbox/inbox.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AuthGuard } from '../app/auth/guards/auth-guard.service';

const routes: Routes = [
  {
    path:'',
    redirectTo: 'accommodation',
    pathMatch: 'full'
  },
  {
    path:'signup',
    component: SignUpComponent
  },
  {
    path:'signin',
    component: SignInComponent
  },
  {
    path:'accommodation',
    component: AccommodationComponent
  },
  {
    path:'account',
    component: AccountComponent,
    canActivate: [AuthGuard],
  },
  {
    path:'inbox',
    component: InboxComponent,
    canActivate: [AuthGuard],
  },
  {
    path: '**', 
    component: PageNotFoundComponent
  },
  {
    path: 'pnf-404', 
    component: PageNotFoundComponent
  }

];

export const routing = RouterModule.forRoot(routes, {useHash: true});

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
