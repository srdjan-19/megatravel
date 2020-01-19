import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../../auth/token-storage.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  isAdmin: boolean;
  isLoggedIn: boolean;

  constructor(private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    if (this.tokenStorage.getToken() != null) {
      this.isLoggedIn = true;

      if (this.tokenStorage.getAuthorities().includes('ROLE_ADMIN')) {
        this.isAdmin = true;
      }
    }
  }

  signOut() {
    window.localStorage.clear();
    this.isLoggedIn = false;
    this.isAdmin = false;
  }


}
