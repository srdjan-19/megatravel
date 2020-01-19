import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { Agent } from '../../model/agent.model';
import { Message, CreateMessageRequest } from '../../model/message.model';
import { MessageService } from '../../services/message.service';


@Component({
  selector: 'app-inbox',
  templateUrl: './inbox.component.html',
  styleUrls: ['./inbox.component.css']
})
export class InboxComponent implements OnInit {
  activeIndex: number;
  agents: Agent[] = [];
  agent: Agent;

  chat: Message[];
  request = new CreateMessageRequest();

  agentName = "";

  lastClientMessageIndex: number;
  firstClientMessageIndex: number;

  constructor(private messageService: MessageService, private notification: ToastrService) { }

  ngOnInit() {
    this.agent = new Agent();
    this.messageService.inbox().subscribe(
      response => {
        this.agents = response;
        this.agent = this.agents[0];
        this.messageService.chat(this.agent.username).subscribe(
          response => {
            this.chat = response;
            this.agentName = this.agent.firstName + " " + this.agent.lastName;
            this.firstClientMessageIndex = 0;
            this.lastClientMessageIndex = this.chat.length - 1;
          },
          error => {
            this.notification.error(error.error.message, "Error", { timeOut: 2000 })
          })
      },
      error => {
        this.notification.error(error.error.message, "Error", { timeOut: 2000 })
      })
  }

  showHistory(index: number) {
    this.activeIndex = index;
    this.agent = this.agents[index];
    this.messageService.chat(this.agent.username).subscribe(
      response => {
        this.chat = response;
        this.agentName = this.agent.firstName + " " + this.agent.lastName;
        this.firstClientMessageIndex = 0;
        this.lastClientMessageIndex = this.chat.length - 1;
      },
      error => {
        this.notification.error(error.error.message, "Error", { timeOut: 2000 })
      })
  }

  send() {
    this.request.recipient = this.agent.username;

    this.messageService.send(this.request).subscribe(
      data => {
        this.chat.push(data);
        let x = document.getElementById("AM").innerHTML = "<p>HELLO</p>";
        console.log(x);
        this.request.content = "";
        this.lastClientMessageIndex = this.chat.length - 1;
      },
      error => {
        this.notification.error(error.error.message, "Error", { timeOut: 2000 })
      }
    )
  }

}
