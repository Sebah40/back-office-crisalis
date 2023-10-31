import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { IUserGet } from '../../model/UserGet.model';

@Component({
  selector: 'app-user',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
})
export class UserListComponent implements OnInit {
  public userListData: IUserGet[] = [];
  public editableValue: boolean = true;

  constructor(public userService: UserService) {}

  ngOnInit(): void {
    this.userService.userListData$.subscribe((users) => {
      this.userListData = users; // Actualiza la lista de usuarios
    });

    // Inicialmente, obtén la lista de usuarios
    this.userService.getAll().subscribe((users) => {
      this.userListData = users;
    });
  }
  deleteUser(entity: any) {
    console.log('userlist', entity);
    const user: { username: string } = { username: entity.username };
    console.log('userList', user);
    this.userService.delete(user).subscribe({
      next: (response: any) => {
        this.userService.updateUserListData();
        alert(response.mensaje);
      },
      error: (error: any) => {
        console.log(error);
      },
    });
  }
}
