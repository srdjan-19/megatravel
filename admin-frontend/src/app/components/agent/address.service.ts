import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Address } from '../../model/address.model';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  private zuurl = 'https://localhost:8443/';
  private addressesURL = this.zuurl + 'main-backend/addresses/';

  constructor(private http: HttpClient) { }

  fetchAddresses(): Observable<Address[]> {
    return this.http.get<Address[]>(this.addressesURL).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(err: HttpErrorResponse) {
    alert(err.error.message);
    return throwError(err.error.message);
  }

}
