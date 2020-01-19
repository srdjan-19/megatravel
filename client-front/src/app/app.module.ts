import { HomeComponent } from './components/home/home.component';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SignUpComponent } from './auth/sign-up/sign-up.component';
import { SignInComponent } from './auth/sign-in/sign-in.component';
import { HttpClientModule } from '@angular/common/http';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { AccommodationComponent } from './components/accommodation/accommodation.component';
import { AccountComponent } from './components/account/account.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './auth/auth-interceptor';
import { InboxComponent } from './components/inbox/inbox.component';
import { PageNotFoundComponent } from './shared/page-not-found/page-not-found.component';
import { AccommodationDetailsComponent } from './components/accommodation/accommodation-details/accommodation-details.component';
import { SendMessageComponent } from './components/inbox/send-message/send-message.component';
import { PostCommentComponent } from './components/post-comment/post-comment.component';
import { ToastrModule } from 'ngx-toastr';

@NgModule({
  declarations: [
    AppComponent,
    SignUpComponent,
    SignInComponent,
    NavbarComponent,
    AccommodationComponent,
    AccountComponent,
    InboxComponent,
    PageNotFoundComponent,
    AccommodationDetailsComponent,
    SendMessageComponent,
    PostCommentComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AngularFontAwesomeModule,
    ToastrModule.forRoot({
      positionClass: "toast-bottom-left",
      maxOpened: 3,
      autoDismiss: true,
      progressBar: true,
      preventDuplicates: true,
    }),
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],

  bootstrap: [AppComponent]
})
export class AppModule { }
