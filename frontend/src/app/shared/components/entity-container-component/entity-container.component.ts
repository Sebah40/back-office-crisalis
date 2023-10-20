import { Component, Input } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';
import { IUserGet } from 'src/app/users/model/UserGet.model';
import { UserService } from 'src/app/users/services/user.service';

@Component({
  selector: 'app-entity-container-component',
  templateUrl: './entity-container.component.html',
  styleUrls: ['./entity-container.component.css'],
})
export class EntityContainerComponent<T extends object> {
  @Input() entities: T[] = [];
  entityKeys: (keyof T)[] = [];
  @Input() title: string = '';
  @Input() isEditable: boolean = false;

  constructor(private userService: UserService, private router: Router) {}
  ngOnChanges() {
    if (this.entities && this.entities.length > 0) {
      this.entityKeys = Object.keys(this.entities[0]) as (keyof T)[];
    }
    console.log(this.entities);
  }

  getData(): any[] {
    console.log(
      this.entities.map((item) => this.entityKeys.map((key) => item[key]))
    );
    return this.entities.map((item) => this.entityKeys.map((key) => item[key]));
  }

  deleteUserHandler(entity: any) {
    const user: { username: string } = { username: entity.username };
    this.userService.deleteUser(user).subscribe({
      next: (response: any) => {
        this.userService.updateUserListData();
        alert(response.mensaje);
      },
      error: (error: any) => {
        console.log(error);
      },
    });
  }

  redirectToEdit(entity: any) {
    console.log(entity);
    const navigationExtras: NavigationExtras = {
      state: entity,
    };
    this.router.navigate(['/user/edit', entity['username']], navigationExtras);
  }

  goToCreateForm() {
    this.router.navigate(['/user/create']);
  }
}
