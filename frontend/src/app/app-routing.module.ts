import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from '../app/login/login.component';
import { HomepageComponent } from './homepage/homepage.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { ClientListComponent } from './client-list/client-list.component';
import { PersonListComponent } from './person-list/person-list.component';
import { EnterpriseFormComponent } from './enterprise-form/enterprise-form.component';
import { PersonFormComponent } from './person-form/person-form.component';
import { TaxListComponent } from './tax-list/tax-list.component';
import { TaxCreateComponent } from './tax-create/tax-create.component';

const routes: Routes = [
  {path:'home', component: HomepageComponent},
  {path:'login', component: LoginComponent},
  {path:'enterprise', component: ClientListComponent},
  {path:'enterprise/getAll', component: ClientListComponent},
  {path:'enterprise/create', component: EnterpriseFormComponent},
  {path:'enterprise/edit/:id', component: EnterpriseFormComponent},
  {path:'enterprise/disable', component: ClientListComponent},
  {path:'person', component: PersonListComponent},
  {path:'person/getAll', component: PersonListComponent},
  {path:'person/create', component: PersonFormComponent},
  {path:'person/edit/:id', component: PersonFormComponent},
  {path:'person/disable', component: PersonListComponent},
  {path:'taxlist', component:TaxListComponent},
  {path:'tax/create', component:TaxCreateComponent},
  {path:'tax/edit/:id', component:TaxCreateComponent},
  {path: '', redirectTo: 'login', pathMatch: 'full' },
  {path:'**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
