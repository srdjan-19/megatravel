import { Injectable, OnInit } from '@angular/core';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError, Observable } from 'rxjs';
import { AccommodationCategory, CreateAccommodationCategoryRequest, UpdateAccommodationCategoryRequest, DeleteAccommodationCategoryRequest } from './accommodation-category';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AccommodationCategoryService {

  private zuurl = 'https://localhost:8443/';
  private categoriesURL = this.zuurl + 'main-backend/accommodation-categories/';

  private createRequest: CreateAccommodationCategoryRequest;

  constructor(private http: HttpClient) {
  }

  fetchCategories(): Observable<AccommodationCategory[]> {
    return this.http.get<AccommodationCategory[]>(this.categoriesURL).pipe(
      catchError(this.handleError)
    )
  }
  fetchCategoryById(id: number): Observable<AccommodationCategory> {
    return this.http.get<AccommodationCategory>(this.categoriesURL + id).pipe(
      catchError(this.handleError)
    )
  }

  create(newCat: string): Observable<AccommodationCategory> {
    this.createRequest = new CreateAccommodationCategoryRequest();
    this.createRequest.name = newCat;
    return this.http.post<AccommodationCategory>(this.categoriesURL, this.createRequest).pipe(
      catchError(this.handleError)
    );
  }

  remove(id: number): Observable<number> {
    return this.http.delete<number>(this.categoriesURL + id);
  }

  update(update: UpdateAccommodationCategoryRequest): Observable<AccommodationCategory> {
    return this.http.put<AccommodationCategory>(this.categoriesURL, update);
  }

  private handleError(err: HttpErrorResponse) {
    alert(err.error.message);
    return throwError(err.error.message);
  }

}
