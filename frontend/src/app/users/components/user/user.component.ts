import { Component } from '@angular/core';
import { IUser } from '../../model/User.model';
import { UserService } from '../../services/user.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent {
  public userListData$!: Observable<IUser[]>;

  constructor(private userService: UserService) {
    this.userListData$ = userService.getAll();
  }
}
