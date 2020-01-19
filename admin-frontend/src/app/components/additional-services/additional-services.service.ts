import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { AdditionalService, UpdateAdditionalServiceRequest } from './additional-services';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AdditionalServicesService implements OnInit {

  private zuurl = 'https://localhost:8443/';

  private additionalServicesURL = this.zuurl + 'main-backend/additional-services/'

  constructor(private http: HttpClient) { }

  fetchAdditionalServices(): Observable<AdditionalService[]> {
    return this.http.get<AdditionalService[]>(this.additionalServicesURL).pipe(
      catchError(this.handleError)
    )
  }

  remove(id: number): Observable<number> {
    return this.http.delete<number>(this.additionalServicesURL + id).pipe(
      catchError(this.handleError)
    );
  }

  create(service: AdditionalService): Observable<AdditionalService> {
    return this.http.post<AdditionalService>(this.additionalServicesURL, service).pipe(
      catchError(this.handleError)
    );
  }

  update(request: UpdateAdditionalServiceRequest): Observable<AdditionalService> {
    return this.http.put<AdditionalService>(this.additionalServicesURL, request).pipe(
      catchError(this.handleError)
    );
  }

  ngOnInit() {
  }

  private handleError(err: HttpErrorResponse) {
    alert(err.error.message);
    return throwError(err.error.message);
  }
}
