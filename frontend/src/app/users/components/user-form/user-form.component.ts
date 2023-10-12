import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { IUser } from '../../model/User.model';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css'],
})
export class UserFormComponent implements OnInit {
  public formUser!: FormGroup;
  public editOrCreateText: string = '';
  public isEditing: boolean = true;

  private userEdit: IUser = {
    name: 'gonza',
    username: 'jafleitas',
    email: 'gonzalo@gmail.com',
    rol: ['admin'],
  };

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.formUser = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(4)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      rol: ['', Validators.required],
    });

    this.setButtonText();
  }

  onSubmit() {
    const newUser: IUser = {
      name: this.formUser.value.name,
      username: this.formUser.value.username,
      email: this.formUser.value.email,
      rol: [this.formUser.value.rol],
    };
    this.createUser(newUser);
  }

  setButtonText() {
    if (this.userEdit.username) {
      this.editOrCreateText = 'Editar usuario';
    } else {
      this.editOrCreateText = 'Crear usuario';
    }
  }

  createUser(user: IUser) {
    this.userService.create(user);
  }
}
