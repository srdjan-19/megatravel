import { Injectable } from '@angular/core';

import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { Accommodation } from '../model/accommodation.model';
import { CreateReservationRequest, CudReservationResponse, CancelReservationRequest } from '../model/reservation.model';


import { Observable, throwError} from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  private zuurl: string;

  constructor(private http: HttpClient) {
    this.zuurl = 'https://localhost:8443/'; 
   }

   public create(reservation: CreateReservationRequest) : Observable<CudReservationResponse> { 
     return this.http.post<CudReservationResponse>(this.zuurl + "main-backend/reservations/", reservation);
   }

   public cancel(reservation: CancelReservationRequest) : Observable<CudReservationResponse> { 
    return this.http.post<CudReservationResponse>(this.zuurl + "main-backend/reservations/cancel/", reservation);
  }

}
