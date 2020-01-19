import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { HttpErrorResponse, HttpClient } from '@angular/common/http';
import { Reservation } from '../model/reservation.model';

@Injectable({
  providedIn: 'root'
})
export class AgentService {

  private zuurl = 'https://localhost:8443';
  private reservationsURL = this.zuurl + "/agent-backend/agents/reservations";

  constructor(private http: HttpClient) {
  }

  public fetchReservedAccommodations(): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(this.reservationsURL);
  }

  private handleException(err: HttpErrorResponse): Observable<never> {
    return throwError(err.message);
  }

}
