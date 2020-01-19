import { Injectable } from '@angular/core';
import { Accommodation, CreateAccommodationRequest, UpdateAccommodationRequest } from '../model/accommodation.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccommodationService {

  private zuurl = 'https://localhost:8443'
  private accommodationsURL = this.zuurl + '/agent-backend/accommodations'
  private ownedAccommodations = this.accommodationsURL + '/owned';

  constructor(private http: HttpClient) { }

  fetchOwned() {
    return this.http.get<Accommodation[]>(this.ownedAccommodations);
  }

  create(request: CreateAccommodationRequest): Observable<Accommodation> {
    return this.http.post<Accommodation>(this.accommodationsURL, request);
  }

  update(update: UpdateAccommodationRequest): Observable<Accommodation> {
    return this.http.put<Accommodation>(this.accommodationsURL, update);
  }

  delete(accommodation: Accommodation): Observable<Accommodation> {
    return this.http.delete<Accommodation>(this.accommodationsURL + accommodation.id);
  }

}
