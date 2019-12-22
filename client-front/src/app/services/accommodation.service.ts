import { Injectable } from '@angular/core';

import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { Accommodation, SearchAccommodationRequest } from '../model/accommodation.model';
import { Observable, throwError} from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Reservation } from '../model/reservation.model';

@Injectable({
  providedIn: 'root'
})
export class AccommodationService {
  

  private zuurl: string;

  constructor(private http: HttpClient) {
    this.zuurl = 'https://localhost:8443/';
   }

   public getAccommodations() : Observable<Accommodation[]> {
     return this.http.get<Accommodation[]>(this.zuurl + "main-backend/accommodations/");
   }

   search(criteria: SearchAccommodationRequest) : Observable<Accommodation[]> { 
     return this.http.get<Accommodation[]>(this.zuurl + "main-backend/accommodations/q?=" + criteria.name + "&" + criteria.type + "&" + criteria.category);
  }
   
}
