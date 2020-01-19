import { ToastrService } from 'ngx-toastr';
import { catchError } from 'rxjs/operators';
import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

import { SigninRequest, SigninResponse } from '../model/signin.model';
import { SignupRequest, SignupResponse } from '../model/signup.model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService implements OnInit {

  ngOnInit(): void {
    throw new Error("Method not implemented.");
  }

  private signupUrl = 'https://localhost:8443/client-registration-service/';
  private signinUrl = 'https://localhost:8443/login-service/';

  constructor(private http: HttpClient,
    private notificationService: ToastrService) {
  }

  signUp(request: SignupRequest): Observable<SignupResponse> {
    return this.http.post<SignupResponse>(this.signupUrl, request, httpOptions);
  }

  signIn(credentials: SigninRequest): Observable<SigninResponse> {
    return this.http.post<SigninResponse>(this.signinUrl, credentials, httpOptions);
  }

}
