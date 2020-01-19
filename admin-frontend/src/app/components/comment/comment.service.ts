import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpClient } from '@angular/common/http';
import { throwError, Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Comment, UpdateCommentRequest, CommentsUpdateResponse } from './comment';
@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private zuurl = 'https://localhost:8443/';

  private commentsURL = this.zuurl + 'main-backend/comments/';
  private commentsByStatusURL = this.zuurl + 'main-backend/comments/search?status=';
  private deleteByUserIdURL = this.commentsURL + "user/";

  constructor(private http: HttpClient) { }

  fetchComments(): Observable<Comment[]> {
    return this.http.get<Comment[]>(this.commentsURL).pipe(
      catchError(this.handleError)
    );
  }

  fetchCommentsByStatus(status: boolean): Observable<Comment[]> {
    return this.http.get<Comment[]>(this.commentsByStatusURL + status).pipe(
      catchError(this.handleError)
    );
  }

  update(update: UpdateCommentRequest): Observable<Comment> {
    return this.http.put<Comment>(this.commentsURL, update).pipe(
      catchError(this.handleError)
    );
  }

  deleteUserComments(username: string): Observable<number> {
    return this.http.delete<number>(this.deleteByUserIdURL + username).pipe(
      catchError(this.handleError)
    )
  }

  private handleError(err: HttpErrorResponse) {
    alert(err.error.message);
    return throwError(err.error.message);
  }

}
