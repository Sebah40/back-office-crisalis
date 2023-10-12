import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserComponent } from './components/user/user.component';
import { UserFormComponent } from './components/user-form/user-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [UserComponent, UserFormComponent],
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  exports: [UserComponent, UserFormComponent],
})
export class UsersModule {}
