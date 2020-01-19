import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'src/app/auth/token-storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  username: string;
  roles: String[];
  permission: boolean;

  constructor(private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    this.username = this.tokenStorage.getUsername();
    this.roles = this.tokenStorage.getAuthorities();

    if (this.tokenStorage.getAuthorities().includes('ROLE_AGENT')) {
      this.permission = true;
    }

  }

}
