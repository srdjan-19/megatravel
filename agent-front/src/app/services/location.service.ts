import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TokenStorageService } from '../auth/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  private cagedata = 'https://api.opencagedata.com/geocode/v1/json?key=e3f9bfba836a4f2a8061966220021fcc&q=';

  constructor(private http: HttpClient, private token: TokenStorageService) {
  }

  public findLocation(lat: number, lng: number): Observable<any> {
    return this.http.get<any>(this.cagedata + lat + "+" + lng, { headers: null });
  }

}
