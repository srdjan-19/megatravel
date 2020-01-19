import { ToastrService } from 'ngx-toastr';
import { MessageService } from '../../../services/message.service';
import { CreateMessageRequest } from '../../../model/message.model';
import { Reservation } from '../../../model/reservation.model';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-send-message',
  templateUrl: './send-message.component.html',
  styleUrls: ['./send-message.component.css']
})
export class SendMessageComponent implements OnInit {

  @Input() reservation = new Reservation();

  name = "";
  messageContent: string;
  message = new CreateMessageRequest();

  constructor(private messageService: MessageService, private notification: ToastrService) { }

  ngOnInit() {
    this.name = this.reservation.accommodation.ownedBy.firstName + " " + this.reservation.accommodation.ownedBy.lastName
  }

  send(): void {
    this.message.recipient = this.reservation.accommodation.ownedBy.username;
    this.messageService.send(this.message).subscribe(
      data => {
        this.notification.success(`Your message has been sent!\nYou can continue chating with ${this.name} in your inbox!`, "Success", { timeOut: 5000 })
      },
      error => {
        this.notification.error(error.error.message)
      })
  }

}
