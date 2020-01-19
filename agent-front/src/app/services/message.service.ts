import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Message, CreateMessageRequest } from '../model/message.model';
import { Observable } from 'rxjs';
import { EndUser } from '../model/endUser.model';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private zuurl = 'https://localhost:8443';
  private messagesURL = this.zuurl + "/agent-backend/messages";
  private inboxURL = this.messagesURL + "/inbox";
  private historyURL = this.messagesURL + "/user/"

  constructor(private http: HttpClient) {
  }

  public send(message: CreateMessageRequest): Observable<Message> {
    return this.http.post<Message>(this.messagesURL, message);
  }

  public inbox(): Observable<EndUser[]> {
    return this.http.get<EndUser[]>(this.inboxURL);
  }

  public chat(withClient: string): Observable<Message[]> {
    return this.http.get<Message[]>(this.historyURL + withClient);
  }

}
