import { Component, OnInit } from '@angular/core';
import { EndUser } from './end-user';
import { EndUserService } from './end-user.service';
import { CommentService } from '../comment/comment.service';
import { TokenStorageService } from '../../auth/token-storage.service';

@Component({
  selector: 'app-end-user',
  templateUrl: './end-user.component.html',
  styleUrls: ['./end-user.component.css']
})
export class EndUserComponent implements OnInit {

  endUsers: EndUser[] = [];
  isAdmin: boolean;
  activatedIndex = -1;
  blockedIndex = -1;

  constructor(private endUserService: EndUserService,
    private commentService: CommentService,
    private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    this.endUserService.fetchEndUsers().subscribe(
      endUsers => this.endUsers = endUsers
    );

    if (this.tokenStorage.getAuthorities().includes('ROLE_ADMIN'))
      this.isAdmin = true;
  }

  activate(client: EndUser) {
    this.endUserService.activate(client.username).subscribe(response => {
      let index = this.endUsers.indexOf(client);
      this.endUsers[index] = response;

      this.activatedIndex = index;
      setTimeout(fade => this.activatedIndex = -1, 2000);
    });
  }

  block(client: EndUser) {
    this.endUserService.block(client.username).subscribe(
      response => {
        let index = this.endUsers.indexOf(client);
        this.endUsers[index] = response;

        this.blockedIndex = index;
        setTimeout(fade => this.blockedIndex = -1, 2000);
      }
    );
  }

  delete(endUser: EndUser) {
    this.commentService.deleteUserComments(endUser.username).subscribe();
    this.endUserService.delete(endUser.username).subscribe(response => {
      let index = this.endUsers.indexOf(endUser);
      this.endUsers.splice(index, 1);
    });
  }

}
