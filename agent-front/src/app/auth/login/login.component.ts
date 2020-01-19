import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { TokenStorageService } from '../token-storage.service';
import { Router } from '@angular/router';
import { SigninRequest } from '../../model/signin.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  isLoggedIn = false;
  isLoginFailed = false;
  error: string;
  roles: string[] = [];

  login = new SigninRequest();

  constructor(private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private router: Router) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getAuthorities();
    }
  }

  signin() {
    this.authService.signIn(this.login).subscribe(
      success => {
        this.tokenStorage.saveToken(success.accessToken);
        this.tokenStorage.saveUsername(success.username);
        this.tokenStorage.saveAuthorities(success.grantedAuthorities);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getAuthorities();

        setTimeout(() => {
          window.location.reload();
        }, 1);

        this.router.navigate(['']);
      },
      error => {
        alert(error.error.message);
        this.error = error.error.message;
        this.isLoginFailed = true;
      }
    );
  }
}
