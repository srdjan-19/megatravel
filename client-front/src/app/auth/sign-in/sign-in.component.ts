import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

import { Router } from '@angular/router';
import { SigninRequest } from 'src/app/model/signin.model';
import { AuthService } from 'src/app/auth/auth.service';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
declare var $: any;

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {
  @Output() signed = new EventEmitter();

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
    private router: Router,
    private notificationService: ToastrService) { }

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

    this.authService.signIn(this.login).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUsername(data.username);
        this.tokenStorage.saveAuthorities(data.grantedAuthorities);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getAuthorities();
        this.router.navigate(['/accommodations']);
        $('#signin').modal('hide');
        this.signed.emit();
      },
      error => {
        this.notificationService.error(error.error.message, "Error", { timeOut: 2000 });
        this.errorMessage = error.error.message;
        this.isLoginFailed = true;
      })

  }

  signOut() {
    this.tokenStorage.signOut();
    this.router.navigate(['/accommodation']);
  }

}
