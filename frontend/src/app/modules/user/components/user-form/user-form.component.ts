import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { IUser } from '../../model/User.model';
import { UserService } from '../../services/user.service';
import { Observable } from 'rxjs';
import { ResponseCreate } from 'src/app/components/interfaces/ResponseCreate.type';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { SweetAlertService } from '../../../shared/service/sweet-alert.service';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css'],
})
export class UserFormComponent implements OnInit {
  public formUser!: FormGroup;
  public editOrCreateText: string = '';
  public isEditing: boolean = false;

  public userEdit: IUser = {
    name: '',
    username: '',
    email: '',
    roles: [],
    password: '',
  };

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private sweet: SweetAlertService
  ) {}

  ngOnInit(): void {
    const state = history.state;
    this.route.params.subscribe((params) => {
      const username = params['username'];
      if (username) {
        this.userEdit.username = username;
        this.userEdit.name = state.name;
        this.userEdit.email = state.email;
        this.userEdit.roles = state.role == 'Usuario' ? ['user'] : ['admin'];
      }
    });

    this.isEditing = this.userEdit.username.length ? true : false;

    this.formUser = this.formBuilder.group({
      name: [
        this.userEdit.name,
        [Validators.required, Validators.minLength(4)],
      ],
      username: [
        this.userEdit.username,
        [Validators.required, Validators.minLength(4)],
      ],
      email: [this.userEdit.email, [Validators.required, Validators.email]],
      password: [
        '',
        this.isEditing ? null : [Validators.required, Validators.minLength(8)],
      ],
      roles: [this.userEdit.roles[0], Validators.required],
    });

    this.setButtonText();
  }

  onSubmit() {
    const newUser: IUser = {
      name: this.formUser.value.name,
      username: this.formUser.value.username,
      email: this.formUser.value.email,
      roles: [this.formUser.value.roles],
      password: this.formUser.value.password,
    };
    if (this.isEditing) {
      this.editUser(newUser).subscribe({
        next: (response) => {
          if ('mensaje' in response) {
            this.sweet.showAlert(
              response.mensaje,
              'success',
              this.goToUserList()
            );
          } else {
            throw response;
          }
        },
        error: (error: HttpErrorResponse) => {
          this.sweet.showAlert(error.error.mensaje, 'error', this.goToUserList);
        },
      });
    } else {
      this.createUser(newUser).subscribe({
        next: (response) => {
          if ('mensaje' in response) {
            this.sweet.showAlert(
              response.mensaje,
              'success',
              this.goToUserList
            );
          } else {
            throw response;
          }
        },
        error: (error: HttpErrorResponse) => {
          this.sweet.showAlert(error.error.mensaje, 'error');
        },
      });
    }
  }

  setButtonText() {
    if (this.userEdit.username) {
      this.editOrCreateText = 'Editar usuario';
    } else {
      this.editOrCreateText = 'Crear usuario';
    }
  }

  createUser(user: IUser): Observable<ResponseCreate> {
    return this.userService.create(user);
  }

  editUser(user: IUser): Observable<ResponseCreate> {
    return this.userService.edit(user);
  }

  goToUserList() {
    this.router.navigate(['/user']);
  }
}
