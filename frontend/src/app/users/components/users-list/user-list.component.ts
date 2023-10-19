import { Component } from '@angular/core';

import { UserService } from '../../services/user.service';

import { IUserGet } from '../../model/UserGet.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
})
export class UserListComponent {
  public userListData: IUserGet[] = [];
  public editableValue: boolean = true;

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.userService.getAll().subscribe((users) => {
      this.userListData = users;
    });
  }

  // goToCreateForm() {
  //   this.router.navigate(['/user/create']);
  // }
}
