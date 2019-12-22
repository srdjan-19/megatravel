import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, NgModel } from '@angular/forms';

import { Router, RouterLink } from '@angular/router';

import { SigninRequest } from 'src/app/model/signin.model';
import { AuthService } from 'src/app/auth/auth.service';
import { TokenStorageService } from 'src/app/auth/token-storage.service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {

  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];

  private login: SigninRequest;

  public account = 'assets/user.png';

  signRequest = new FormGroup({
    username: new FormControl(''),
    password: new FormControl('')
  })

  constructor(private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private router: Router) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getAuthorities();
    }
  }

  get user() {
    return this.signRequest.controls;
  }

  signIn(): void {
    this.login = new SigninRequest()
    this.login.username = this.user.username.value
    this.login.password = this.user.password.value

    console.log(this.login);

    this.authService.signIn(this.login).subscribe(
      data => {
        console.log(data)
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUsername(data.username);
        this.tokenStorage.saveAuthorities(data.grantedAuthorities);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getAuthorities();
        window.location.reload();
        this.router.navigate(['/accommodation']);
      },
      error => {
        alert(error.error.message);
        this.errorMessage = error.error.message;
        this.isLoginFailed = true;
      })

  }

  signOut() {
    this.tokenStorage.signOut();
    this.router.navigate(['/accommodation']);
  }

}
