import { Component, Input } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';
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
  }

  getData(): any[] {
    return this.entities.map((item) => this.entityKeys.map((key) => item[key]));
  }

  deleteUserHandler(username: { username: string }) {
    this.userService.delete(username).subscribe({
      next: (response) => {
        console.log(response);
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  redirectToEdit(username: string, rowData: string[]) {
    const navigationExtras: NavigationExtras = {
      state: rowData,
    };
    this.router.navigate(['/user/edit', username], navigationExtras);
  }

  goToCreateForm() {
    this.router.navigate(['/user/create']);
  }
}
