import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AccommodationCategory } from '../model/accommodationCategory.model';
import { AccommodationType } from '../model/accommodationType.model';
import { AdditionalService } from '../model/additionalservice.model';

@Injectable({
  providedIn: 'root'
})
export class CodebookService {

  private zuurl = 'https://localhost:8443'

  private categoriesURL = this.zuurl + '/agent-backend/accommodation-categories'
  private typesURL = this.zuurl + '/agent-backend/accommodation-types'
  private additionalServicesURL = this.zuurl + '/agent-backend/additional-services'

  constructor(private http: HttpClient) { }

  fetchCategories(): Observable<AccommodationCategory[]> {
    return this.http.get<AccommodationCategory[]>(this.categoriesURL)
  }

  fetchTypes(): Observable<AccommodationType[]> {
    return this.http.get<AccommodationType[]>(this.typesURL)
  }

  fetchAdditionalServices(): Observable<AdditionalService[]> {
    return this.http.get<AdditionalService[]>(this.additionalServicesURL)
  }

}
