import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Reservation, UpdateReservationRequest } from '../model/reservation.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  private zuurl = 'https://localhost:8443';
  private reservationsURL = this.zuurl + '/agent-backend/reservations/';

  constructor(private http: HttpClient) { }

  update(update: UpdateReservationRequest): Observable<Reservation> {
    return this.http.put<Reservation>(this.reservationsURL, update);
  }

}
