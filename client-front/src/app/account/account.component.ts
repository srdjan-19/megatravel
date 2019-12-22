import { Component, OnInit } from '@angular/core';

import { UsersService } from '../services/users.service';
import { MessageService } from '../services/message.service';
import { CommentService } from '../services/comment.service';


import { Reservation } from "../model/reservation.model";
import { Agent } from 'src/app/model/agent.model';
import { Message, CreateMessageRequest, CreateMessageResponse } from '../model/message.model';
import { CreateCommentRequest, CreateCommentResponse } from '../model/comment.model';


@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css'],
  styles: [`textarea {
    resize: none;
  }
  .rating {
    font-size: 48px;
    color: orange;
    display: inline-block;
    overflow: hidden;
  }
  .rating::before { 
    content: "★★★★★" 
  }`]  
})
export class AccountComponent implements OnInit {

  reservations: Reservation[];
  reservation: Reservation;

  message: CreateMessageRequest;

  comment: CreateCommentRequest;
 
  _messageContent: string;
  _commentContent: string;

  constructor(private userService: UsersService,
              private messageService: MessageService,
              private commentService: CommentService) { }

  get messageContent() : string { 
    return this._messageContent;
  }

  set messageContent(c : string) {
    this._messageContent = c;
  }

  get commentContent() : string { 
    return this._commentContent;
  }

  set commentContent(c : string) {
    this._commentContent = c;
  }

  ngOnInit() {
    this.userService.findMyReservations().subscribe(
      data => {
        this.reservations = data;
      },
      err => {
        console.log(err.error)
      }
    )
  }

  postComment(accommodationName: string, fromDate: Date, tillDate: Date) : void{

    this.comment = new CreateCommentRequest();
    this.comment.accommodationName = accommodationName;
    this.comment.content = this.commentContent;
    this.comment.fromDate = fromDate;
    this.comment.tillDate = tillDate;

    this.commentService.postComment(this.comment).subscribe(
      data => {
        alert(data.feedback)
      },
      err =>{
        alert(err.error.message)
      })

  }

  sendMessage(recipient : string) : void {

    this.message = new CreateMessageRequest();
    this.message.content = this.messageContent;
    this.message.recipient = recipient;

    this.messageService.sendMessage(this.message).subscribe( 
      data => {
        alert(data)
    },
      err => {
        alert(err.error.message)
    })
  }


}
