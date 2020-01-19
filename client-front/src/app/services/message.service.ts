import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { Observable, throwError } from 'rxjs';
import { Message, CreateMessageRequest, CreateMessageResponse } from '../model/message.model';
import { Agent } from '../model/agent.model';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private zuurl = 'https://localhost:8443';
  private messagesURL = this.zuurl + "/main-backend/messages/";
  private inboxURL = this.messagesURL + "inbox/";
  private chatURL = this.messagesURL + "user/";


  constructor(private http: HttpClient) {
  }

  public send(message: CreateMessageRequest): Observable<Message> {
    return this.http.post<Message>(this.messagesURL, message);
  }

  public inbox(): Observable<Agent[]> {
    return this.http.get<Agent[]>(this.inboxURL);
  }

  public chat(withAgent: string): Observable<Message[]> {
    return this.http.get<Message[]>(this.chatURL + withAgent);
  }

}
