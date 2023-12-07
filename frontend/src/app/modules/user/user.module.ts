import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserListComponent } from './components/users-list/user-list.component';
import { UserFormComponent } from './components/user-form/user-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EntityContainerComponent } from '../shared/components/entity-container-component/entity-container.component';
import { TranslateModule } from '@ngx-translate/core';

import { ProfileComponent } from './components/profile/profile.component';
import { ButtonBackComponent } from '../shared/components/button-back/button-back.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [
    UserListComponent,
    UserFormComponent,
    EntityContainerComponent,
    ProfileComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule.forChild(),
    SharedModule,
  ],
  exports: [
    UserListComponent,
    UserFormComponent,
    EntityContainerComponent,
    ReactiveFormsModule,
  ],
})
export class UsersModule {}
