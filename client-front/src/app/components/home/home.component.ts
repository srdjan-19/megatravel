import { TokenStorageService } from './../../auth/token-storage.service';
import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  constructor(private notification: ToastrService, private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    if (this.tokenStorage.getToken() != null)
      this.notification.info("Welcome " + this.tokenStorage.getUsername())
  }

}
