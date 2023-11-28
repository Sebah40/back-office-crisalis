import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './modules/auth/components/login/login.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { ClientListComponent } from './modules/client/components/enterprise-list/enterprise-list.component';
import { PersonListComponent } from './modules/client/components/person-list/person-list.component';
import { EnterpriseFormComponent } from './modules/client/components/enterprise-form/enterprise-form.component';
import { PersonFormComponent } from './modules/client/components/person-form/person-form.component';
import { TaxListComponent } from './modules/tax/components/tax-list/tax-list.component';
import { TaxCreateComponent } from './modules/tax/components/tax-create/tax-create.component';
import { OrderListComponent } from './order/order-list/order-list.component';
import { OrderComponent } from './order/order-view/order.component';
import { CreateOrderComponent } from './order/create-order/create-order.component';
import { EditOrderComponent } from './order/edit-order/edit-order.component';
import { ClientList2Component } from './modules/client/components/client-list/client-list.component';
import { ClientServicesListComponent } from './modules/client/components/client-services-list/client-services-list.component';
import { userGuard } from './guards/user.guard';
import { loginGuard } from './guards/login.guard';

const routes: Routes = [
  {
    path: 'home',
    component: HomepageComponent,
    canActivate: [userGuard],
  },
  { path: 'login', component: LoginComponent, canActivate: [loginGuard] },
  {
    path: 'enterprise',
    component: ClientListComponent,
    canActivate: [userGuard],
  },
  {
    path: 'enterprise/getAll',
    component: ClientListComponent,
    canActivate: [userGuard],
  },
  {
    path: 'enterprise/create',
    component: EnterpriseFormComponent,
    canActivate: [userGuard],
  },
  {
    path: 'enterprise/edit/:id',
    component: EnterpriseFormComponent,
    canActivate: [userGuard],
  },
  {
    path: 'enterprise/disable',
    component: ClientListComponent,
    canActivate: [userGuard],
  },
  { path: 'person', component: PersonListComponent, canActivate: [userGuard] },
  {
    path: 'person/getAll',
    component: PersonListComponent,
    canActivate: [userGuard],
  },
  {
    path: 'person/create',
    component: PersonFormComponent,
    canActivate: [userGuard],
  },
  {
    path: 'person/edit/:id',
    component: PersonFormComponent,
    canActivate: [userGuard],
  },
  {
    path: 'person/disable',
    component: PersonListComponent,
    canActivate: [userGuard],
  },
  {
    path: 'clients',
    component: ClientList2Component,
    canActivate: [userGuard],
  },
  {
    path: 'clients/:id/services',
    component: ClientServicesListComponent,
    canActivate: [userGuard],
  },
  { path: 'taxlist', component: TaxListComponent, canActivate: [userGuard] },
  {
    path: 'tax/create',
    component: TaxCreateComponent,
    canActivate: [userGuard],
  },
  {
    path: 'tax/edit/:id',
    component: TaxCreateComponent,
    canActivate: [userGuard],
  },
  {
    path: 'order/create',
    component: CreateOrderComponent,
    canActivate: [userGuard],
  },
  {
    path: 'order/getAll',
    component: OrderListComponent,
    canActivate: [userGuard],
  },
  {
    path: 'order/edit/:id',
    component: EditOrderComponent,
    canActivate: [userGuard],
  },
  { path: 'order/:id', component: OrderComponent, canActivate: [userGuard] },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
