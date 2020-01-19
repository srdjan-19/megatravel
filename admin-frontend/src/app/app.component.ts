import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from './auth/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  title = 'Admin page';

  constructor() {
  }

  ngOnInit() {

  }

}


