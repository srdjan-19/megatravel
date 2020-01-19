import { Injectable } from '@angular/core'
import { HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AccommodationType, CreateAccommodationTypeRequest, DeleteAccommodationTypeRequest, UpdateAccommodationTypeRequest } from './accommodation-type';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class AccommodationTypeService {

    private zuurl = 'https://localhost:8443/';
    private typesURL = this.zuurl + 'main-backend/accommodation-types/';

    private createRequest = new CreateAccommodationTypeRequest;


    constructor(private http: HttpClient) {
    }

    fetchTypes(): Observable<AccommodationType[]> {
        return this.http.get<AccommodationType[]>(this.typesURL).pipe(
            catchError(this.handleError)
        )
    }

    remove(id: number): Observable<number> {
        return this.http.delete<number>(this.typesURL + id).pipe(
            catchError(this.handleError)
        );
    }

    create(newType: AccommodationType): Observable<AccommodationType> {
        this.createRequest.name = newType.name;
        return this.http.post<AccommodationType>(this.typesURL, this.createRequest).pipe(
            catchError(this.handleError)
        );
    }

    update(request: UpdateAccommodationTypeRequest): Observable<AccommodationType> {
        return this.http.put<AccommodationType>(this.typesURL, request).pipe(
            catchError(this.handleError)
        );
    }

    private handleError(err: HttpErrorResponse) {
        alert(err.error.message);
        return throwError(err.error.message);
    }
}