import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { Observable, throwError} from 'rxjs';
import { Message, CreateMessageRequest, CreateMessageResponse } from '../model/message.model';
import { Agent } from '../model/agent.model';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private zuurl: string;

  constructor(private http: HttpClient) {
    this.zuurl = 'https://localhost:8443/';
  }

  public sendMessage(message : CreateMessageRequest) : Observable<Message[]> {
    return this.http.post<Message[]>(this.zuurl + "main-backend/messages/", message);
  }

  public inbox() : Observable<Agent[]> {
    return this.http.get<Agent[]>(this.zuurl + "main-backend/messages/inbox/");
  }

  public chat(withAgent : string) : Observable<Message[]> {
    return this.http.get<Message[]>(this.zuurl + "main-backend/messages/history/username=" + withAgent);
  }

}
