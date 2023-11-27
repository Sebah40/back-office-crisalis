import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserFormComponent } from './components/user-form/user-form.component';
import { UserListComponent } from './components/users-list/user-list.component';
import { ProfileComponent } from './components/profile/profile.component';

const routes: Routes = [
  {
    path: 'user',
    component: UserListComponent,
  },
  { path: 'user/edit/:username', component: UserFormComponent },
  { path: 'user/create', component: UserFormComponent },
  { path: 'user/profile', component: ProfileComponent },
  //{ path: '', redirectTo: 'login', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UsersRoutingModule {}
