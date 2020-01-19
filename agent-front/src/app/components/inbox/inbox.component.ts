import { Component, OnInit } from '@angular/core';

import { MessageService } from '../../services/message.service';
import { EndUser } from '../../model/endUser.model';
import { Message, CreateMessageRequest } from '../../model/message.model';


@Component({
  selector: 'app-inbox',
  templateUrl: './inbox.component.html',
  styleUrls: ['./inbox.component.css']
})
export class InboxComponent implements OnInit {

  clients: EndUser[];
  client: EndUser;
  clientName = "";

  chat: Message[];
  request = new CreateMessageRequest();

  constructor(private messageService: MessageService) { }

  ngOnInit() {
    this.client = new EndUser();
    this.messageService.inbox().subscribe(
      response => {
        this.clients = response;
        this.client = this.clients[0];
        this.messageService.chat(this.client.username).subscribe(
          response => {
            this.chat = response;
            this.clientName = this.client.firstName + " " + this.client.lastName;
          },
          error => {
            alert(error.error.message)
          })
      },
      error => {
        alert("You didnt chat with anyone yet!")
      })
  }

  showHistory(index: number) {
    this.client = this.clients[index];
    this.messageService.chat(this.client.username).subscribe(
      response => {
        this.chat = response;
        this.clientName = this.client.firstName + " " + this.client.lastName;
      },
      error => {
        alert(error.error.message)
      })

  }

  send() {
    this.request.recipient = this.client.username;
    this.messageService.send(this.request).subscribe(
      data => {
        this.chat.push(data);
        this.request.content = "";
      },
      error => {
        alert(error.error.message)
      }
    )

  }

}
