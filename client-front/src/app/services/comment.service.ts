import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { Observable, throwError } from 'rxjs';
import { CreateCommentRequest, CreateCommentResponse } from '../model/comment.model';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private zuurl: string;

  constructor(private http: HttpClient) {
    this.zuurl = 'https://localhost:8443';
  }

  public post(comment: CreateCommentRequest): Observable<Comment> {
    return this.http.post<Comment>(this.zuurl + "/main-backend/comments/", comment);
  }


}
