import { BrowserModule } from '@angular/platform-browser';
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from '../app/auth/auth-interceptor';
import { AppComponent } from './app.component';
import { LoginComponent } from './auth/login/login.component';
import { AppRoutingModule } from './app-routing.module'
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { AccommodationsComponent } from './components/accommodations/accommodations.component';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { InboxComponent } from './components/inbox/inbox.component';
import { ReservationComponent } from './components/reservation/reservation.component';
import { CreateAccommodationComponent } from './components/accommodations/create-accommodation/create-accommodation.component';
import { UpdateAccommodationComponent } from './components/accommodations/update-accommodation/update-accommodation.component';
import { MapComponent } from './shared/map/map.component';
import { HomeComponent } from './components/home/home.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavbarComponent,
    AccommodationsComponent,
    PageNotFoundComponent,
    InboxComponent,
    ReservationComponent,
    CreateAccommodationComponent,
    UpdateAccommodationComponent,
    MapComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    AngularFontAwesomeModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  schemas: [NO_ERRORS_SCHEMA],
  bootstrap: [AppComponent]
})
export class AppModule { }
