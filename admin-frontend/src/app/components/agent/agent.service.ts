import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Agent, CreateAgentRequest } from './agent.model';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AgentService {

  private zuurl = 'https://localhost:8443/';

  private createURL = this.zuurl + 'agent-creation-service/';
  private agentsURL = this.zuurl + 'main-backend/agents';

  constructor(private http: HttpClient) { }

  fetchAgents(): Observable<Agent[]> {
    return this.http.get<Agent[]>(this.agentsURL).pipe(
      catchError(this.handleError)
    );
  }

  create(request: CreateAgentRequest): Observable<Agent> {
    return this.http.post<Agent>(this.createURL, request).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(err: HttpErrorResponse) {
    alert(err.error.message);
    return throwError(err.error.message);
  }

}
