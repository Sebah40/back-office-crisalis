import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { IUserGet } from '../../model/UserGet.model';
import { SweetAlertService } from '../../../shared/service/sweet-alert.service';

@Component({
  selector: 'app-user',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
})
export class UserListComponent implements OnInit {
  public userListData: IUserGet[] = [];
  public editableValue: boolean = true;

  constructor(
    public userService: UserService,
    private sweet: SweetAlertService
  ) {}

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
    const user: { username: string } = { username: entity.username };

    this.userService.delete(user).subscribe({
      next: (response: any) => {
        this.userService.updateUserListData();
        this.sweet.showAlert(response.mensaje, 'success');
      },
      error: (error: any) => {
        console.log(error);
        this.sweet.showAlert(error.error.mensaje, 'error');
      },
    });
  }
}
