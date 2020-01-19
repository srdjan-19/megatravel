import { Component, OnInit } from '@angular/core';
import { UsersService } from '../../services/users.service';
import { Reservation } from "../../model/reservation.model";
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css'],

})
export class AccountComponent implements OnInit {

  reservations: Reservation[];

  constructor(private userService: UsersService, private notification: ToastrService) { }

  ngOnInit() {
    this.userService.findMyReservations().subscribe(
      data => {
        this.reservations = data;
        this.notification.info(`All of your ${this.reservations.length} reservations has been retrieved.`);
      },
      error => {
        this.notification.error(error.error.message, "Erorr");
      }
    )

  }

}
