import { Injectable } from '@angular/core';

import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { Accommodation, SearchAccommodationRequest } from '../model/accommodation.model';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Reservation } from '../model/reservation.model';

@Injectable({
  providedIn: 'root'
})
export class AccommodationService {


  private zuurl = 'https://localhost:8443/';
  private accommodationsURL = this.zuurl + 'main-backend/accommodations?page=';
  private searchAccommodationsURL = this.zuurl + 'main-backend/accommodations/search?';

  constructor(private http: HttpClient) {
  }

  public fetchAccommodations(page: number): Observable<Accommodation[]> {
    return this.http.get<Accommodation[]>(this.accommodationsURL + page);
  }

  search(criteria: SearchAccommodationRequest): Observable<Accommodation[]> {
    return this.http.get<Accommodation[]>(this.searchAccommodationsURL + "name=" + criteria.name + "&type=" + criteria.type + "&category=" + criteria.category);
  }

}
