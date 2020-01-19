import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { AccommodationTypeComponent } from './components/accommodation-type/accommodation-type.component';
import { AdditionalServicesComponent } from './components/additional-services/additional-services.component';
import { AdditionalServicesModifyComponent } from './components/additional-services/additional-services-modify/additional-services-modify.component';
import { AccommodationTypeModifyComponent } from './components/accommodation-type/accommodation-type-modify/accommodation-type-modify.component';
import { AccommodationCategoryComponent } from './components/accommodation-category/accommodation-category.component';
import { AccommodationCategoryModifyComponent } from './components/accommodation-category/accommodation-category-modify/accommodation-category-modify.component';
import { EndUserComponent } from './components/end-user/end-user.component';
import { CommentComponent } from './components/comment/comment.component';
import { AgentComponent } from './components/agent/agent.component';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { SigninComponent } from './auth/signin/signin.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { PageNotFoundComponent } from './shared/page-not-found/page-not-found.component';
import { AuthGuard } from './auth/guards/auth-guard.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './auth/auth-interceptor';
import { CreateAgentComponent } from './components/agent/create-agent/create-agent.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { NoDataComponent } from './shared/no-data/no-data.component';

@NgModule({
  declarations: [
    AppComponent,
    AccommodationTypeComponent,
    AdditionalServicesComponent,
    AdditionalServicesModifyComponent,
    AccommodationTypeModifyComponent,
    AccommodationCategoryComponent,
    AccommodationCategoryModifyComponent,
    EndUserComponent,
    CommentComponent,
    AgentComponent,
    SigninComponent,
    PageNotFoundComponent,
    CreateAgentComponent,
    NavbarComponent,
    NoDataComponent,
  ],
  imports: [
    AngularFontAwesomeModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    RouterModule.forRoot([
      { path: 'accommodation-types', component: AccommodationTypeComponent, canActivate: [AuthGuard] },
      { path: 'additional-services', component: AdditionalServicesComponent, canActivate: [AuthGuard] },
      { path: 'accommodation-categories', component: AccommodationCategoryComponent, canActivate: [AuthGuard] },
      { path: 'end-users', component: EndUserComponent, canActivate: [AuthGuard] },
      { path: 'comments', component: CommentComponent, canActivate: [AuthGuard] },
      { path: 'agents', component: AgentComponent, canActivate: [AuthGuard] },
      { path: 'sign-in', component: SigninComponent },
      { path: '**', component: PageNotFoundComponent },
      { path: '404', component: PageNotFoundComponent }
    ])
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
