import { Component, OnInit, Renderer2, ElementRef } from '@angular/core';
import { ReservationService } from '../../services/reservation.service';
import { Reservation, UpdateReservationRequest } from '../../model/reservation.model';
import { AgentService } from '../../services/agent.service';
import { TokenStorageService } from '../../auth/token-storage.service';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {

  reservations: Reservation[]
  update: UpdateReservationRequest;
  approvedIndex: number;
  rejectedIndex: number;

  isAgent: boolean;
  constructor(private reservationService: ReservationService,
    private agentService: AgentService,
    private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    this.agentService.fetchReservedAccommodations().subscribe(
      data => {
        this.reservations = data;
      },
      error => {
        alert(error.error.message)
      }
    )

    if (this.tokenStorage.getAuthorities().includes('ROLE_AGENT')) {
      this.isAgent = true;
    }

  }

  approve(rid: number) {
    this.update = new UpdateReservationRequest();
    this.update.id = rid;
    this.update.status = 'APPROVED';

    this.reservationService.update(this.update).subscribe(
      data => {
        let index = this.reservations.findIndex(current => current.id == rid);
        this.reservations[index] = data;
        this.approvedIndex = index;

        setTimeout(update => this.approvedIndex = -1, 1000);
      }
    );

  }

  reject(rid: number) {
    this.update = new UpdateReservationRequest();
    this.update.id = rid;
    this.update.status = 'REJECTED';

    this.reservationService.update(this.update).subscribe(
      data => {
        let index = this.reservations.findIndex(current => current.id == rid);
        this.reservations[index] = data;
        this.rejectedIndex = index;

        setTimeout(update => this.rejectedIndex = -1, 1000);
      }
    );

  }

}
