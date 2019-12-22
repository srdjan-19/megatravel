import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { SigninRequest } from '../model/signin.model'
import { Observable, throwError} from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Reservation } from '../model/reservation.model';

import { tap } from 'rxjs/operators';
import { Agent } from '../model/agent.model';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private zuurl: string;

  constructor(private http: HttpClient) {
    this.zuurl = 'https://localhost:8443/';
  }

  public findMyReservations() : Observable<Reservation[]>{
    return this.http.get<Reservation[]>(this.zuurl + "main-backend/users/reservations/");
  }

  public inbox() : Observable<Agent[]>{
    return this.http.get<Agent[]>(this.zuurl + "main-backend/messages/inbox/");
  }
  
  private handleException(err: HttpErrorResponse): Observable<never> {
    return throwError(err.message);
  }

}
