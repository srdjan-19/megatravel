import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { EndUser } from './end-user';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EndUserService {

  private zuurl = "https://localhost:8443/"

  private findAllUrl = this.zuurl + 'main-backend/users/endusers';
  private activateURL = this.zuurl + 'main-backend/users/activate/';
  private blockURL = this.zuurl + 'main-backend/users/block/';
  private usersURL = this.zuurl + 'main-backend/users/';

  constructor(private http: HttpClient) { }

  fetchEndUsers(): Observable<EndUser[]> {
    return this.http.get<EndUser[]>(this.findAllUrl).pipe(
      catchError(this.handleError)
    );
  }

  activate(username: string): Observable<EndUser> {
    return this.http.put<EndUser>(this.activateURL + username, null).pipe(
      catchError(this.handleError)
    );
  }

  block(username: string): Observable<EndUser> {
    return this.http.put<EndUser>(this.blockURL + username, null).pipe(
      catchError(this.handleError)
    );
  }

  delete(username: string): Observable<EndUser> {
    return this.http.delete<EndUser>(this.usersURL + username).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(err: HttpErrorResponse) {
    alert(err.error.message);
    return throwError(err.error.message);
  }

}
