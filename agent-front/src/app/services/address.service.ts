import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Address } from '../model/address.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  private zuurl = 'https://localhost:8443/'
  private addressesURL = this.zuurl + 'agent-backend/addresses/'

  constructor(private http: HttpClient) { }

  fetchAddresses(): Observable<Address[]> {
    return this.http.get<Address[]>(this.addressesURL);
  }

}
