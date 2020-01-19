import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../../auth/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  isLoggedIn: boolean;
  isAgent: boolean;

  public logo = 'assets/logo.png'

  constructor(private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    if (this.tokenStorage.getToken() != null) {
      this.isLoggedIn = true;

      if (this.tokenStorage.getAuthorities().includes('ROLE_AGENT'))
        this.isAgent = true;
    }
  }

  signOut() {
    this.tokenStorage.signOut();
    this.isLoggedIn = false;
    this.isAgent = false;

  }

}
