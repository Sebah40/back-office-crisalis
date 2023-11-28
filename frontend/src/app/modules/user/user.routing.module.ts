import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserFormComponent } from './components/user-form/user-form.component';
import { UserListComponent } from './components/users-list/user-list.component';
import { ProfileComponent } from './components/profile/profile.component';
import { userGuard } from 'src/app/guards/user.guard';
import { adminGuard } from 'src/app/guards/admin.guard';

const routes: Routes = [
  {
    path: 'user',
    component: UserListComponent,
    canActivate: [adminGuard],
  },
  {
    path: 'user/edit/:username',
    component: UserFormComponent,
    canActivate: [adminGuard],
  },
  {
    path: 'user/create',
    component: UserFormComponent,
    canActivate: [adminGuard],
  },
  {
    path: 'user/profile',
    component: ProfileComponent,
    canActivate: [userGuard],
  },
  //{ path: '', redirectTo: 'login', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UsersRoutingModule {}
