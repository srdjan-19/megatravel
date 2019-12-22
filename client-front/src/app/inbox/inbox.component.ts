import { Component, OnInit } from '@angular/core';
import { Agent } from '../model/agent.model';
import { Message, CreateMessageRequest} from '../model/message.model';

import { UsersService } from '../services/users.service';
import { MessageService } from '../services/message.service';


@Component({
  selector: 'app-inbox',
  templateUrl: './inbox.component.html',
  styleUrls: ['./inbox.component.css']
})
export class InboxComponent implements OnInit {

  public agents: Agent[];
  private agent : Agent;

  private chat: Message[];
  private message: string;
  private request: CreateMessageRequest;

  private agentName = "";
  private numberOfMessages: number;

  constructor(private messageService: MessageService) { }

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
        this.numberOfMessages = this.chat.length;
        },
        error => { alert(error.error.message) 
      })
    },
      error => { alert("You didnt chat with anyone yet!")
    })
  }

  showHistory(i: number) {
    this.agent = this.agents[i];
    this.messageService.chat(this.agent.username).subscribe(
      response => { 
      this.chat = response;
      this.agentName = this.agent.firstName + " " + this.agent.lastName;
      this.numberOfMessages = this.chat.length;
    },
      error => { alert(error.error.message) })
  }
  
  sendMessage() { 

    this.request = new CreateMessageRequest();
    this.request.content = this.message;
    this.request.recipient = this.agent.username;

    console.log(this.request)
    this.messageService.sendMessage(this.request).subscribe(
      response => {
        this.chat = response;
        this.message = "";
      },
      error => {
        alert(error.error.message)
      }
    )
  }


}
