import { Component, OnInit } from '@angular/core';
import { CommentService } from './comment.service';
import { Comment, UpdateCommentRequest } from './comment';
import { TokenStorageService } from '../../auth/token-storage.service';
@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {

  refusedComments: Comment[] = [];
  acceptedComments: Comment[] = [];
  private update = new UpdateCommentRequest();

  isAdmin: boolean;

  constructor(private commentService: CommentService,
    private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    this.commentService.fetchCommentsByStatus(true).subscribe(
      response => { this.acceptedComments = response }
    );

    this.commentService.fetchCommentsByStatus(false).subscribe(
      response => { this.refusedComments = response }
    );

    if (this.tokenStorage.getAuthorities().includes('ROLE_ADMIN'))
      this.isAdmin = true;

  }

  approve(id: number) {
    this.update.id = id;
    this.update.status = true;
    this.commentService.update(this.update).subscribe(
      response => {
        let index = this.refusedComments.findIndex((current) => current.id == id);
        this.acceptedComments.push(response);
        this.refusedComments.splice(index, 1);
      });
  }

  refuse(id: number) {
    this.update.id = id;
    this.update.status = true;
    this.commentService.update(this.update).subscribe(
      response => {
        let index = this.acceptedComments.findIndex((current) => current.id == id);
        this.refusedComments.push(response);
        this.acceptedComments.splice(index, 1);
      });
  }

}
