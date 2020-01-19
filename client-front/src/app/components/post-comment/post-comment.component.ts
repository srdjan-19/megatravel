import { ToastrService } from 'ngx-toastr';
import { CommentService } from '../../services/comment.service';
import { CreateCommentRequest } from '../../model/comment.model';
import { Reservation } from '../../model/reservation.model';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-post-comment',
  templateUrl: './post-comment.component.html',
  styleUrls: ['./post-comment.component.css']
})
export class PostCommentComponent implements OnInit {

  @Input() reservation: Reservation;

  commentContent: string;
  private comment = new CreateCommentRequest();

  constructor(private commentService: CommentService,
    private notification: ToastrService) { }

  ngOnInit() {
    this.comment.accommodationName = this.reservation.accommodation.name;
    this.comment.fromDate = this.reservation.fromDate;
    this.comment.tillDate = this.reservation.tillDate;
  }

  post(): void {
    this.commentService.post(this.comment).subscribe(
      data => {
        this.notification.success(`Your comment  is on review!`, "Success")
      },
      err => {
        this.notification.error(err.error.message, "Error")
      })
  }

};