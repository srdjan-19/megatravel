import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CreateReservationRequest, CudReservationResponse, CancelReservationRequest } from '../model/reservation.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  private zuurl = 'https://localhost:8443';
  private reservationsURL = this.zuurl + "/main-backend/reservations/";
  private cancelURL = this.reservationsURL + "cancel/";

  constructor(private http: HttpClient) {
  }

  public create(reservation: CreateReservationRequest): Observable<CudReservationResponse> {
    return this.http.post<CudReservationResponse>(this.reservationsURL, reservation);
  }

  public cancel(reservation: CancelReservationRequest): Observable<CudReservationResponse> {
    return this.http.post<CudReservationResponse>(this.cancelURL, reservation);
  }

}
