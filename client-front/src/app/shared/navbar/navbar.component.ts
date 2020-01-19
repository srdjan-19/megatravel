import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '../../auth/auth.service';
import { TokenStorageService } from '../../auth/token-storage.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  isLoggedIn: boolean;
  isEndUser: boolean;

  public logo = 'assets/logo.png'


  constructor(private authService: AuthService,
    private tokenStorageService: TokenStorageService,
    private router: Router) { }

  ngOnInit() {

    if (this.tokenStorageService.getToken() != null) {
      this.isLoggedIn = true;

      if (this.tokenStorageService.getAuthorities().includes('ROLE_END_USER'))
        this.isEndUser = true;
    }

  }

  signout() {
    this.tokenStorageService.signOut();
    this.isLoggedIn = false;
    this.isEndUser = false;
    this.router.navigate(['/home']);
  }

  signed() {
    if (this.tokenStorageService.getToken() != null) {
      this.isLoggedIn = true;

      if (this.tokenStorageService.getAuthorities().includes('ROLE_END_USER'))
        this.isEndUser = true;
    }
  }

}
