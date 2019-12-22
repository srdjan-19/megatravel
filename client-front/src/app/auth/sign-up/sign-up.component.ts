import { Component, OnInit } from '@angular/core';
import { Router} from '@angular/router';
import { FormControl, FormGroup, FormBuilder, NgModel } from '@angular/forms';

import { AuthService } from '../auth.service';
import { SignupRequest } from 'src/app/model/signup.model';


@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  isSignedUp = false;
  isSignUpFailed = false;
  public image ='assets/gradient.jpg';
  private newUser: SignupRequest;
  
  public signupInfo: FormGroup;
  public errorMessageSingup : NgModel;

  constructor(private authService: AuthService,
              private router: Router ) {}

  ngOnInit() {
    this.signupInfo = new FormGroup({
      username: new FormControl(''),
      password: new FormControl(''),
      firstName: new FormControl(''),
      lastName: new FormControl(''),
      email: new FormControl('')
    })
  }

  get user() { 
    return this.signupInfo.controls; 
  }

  signUp() : void {
      this.newUser = new SignupRequest();
      this.newUser.username = this.user.username.value;
      this.newUser.password = this.user.password.value;
      this.newUser.firstName =this.user.firstName.value;
      this.newUser.lastName = this.user.lastName.value;
      this.newUser.email = this.user.email.value;

      this.authService.signUp(this.newUser).subscribe(
        data => {
          this.isSignedUp = true;
          this.isSignUpFailed = false;
          alert(data.feedback)
        },
        error => {
          this.errorMessageSingup = error.error.message;
          this.isSignUpFailed = true;
          alert(error.error.message)
        }
      );  
  }

  signIn() : void {
    window.location.reload();
  }

 
}
