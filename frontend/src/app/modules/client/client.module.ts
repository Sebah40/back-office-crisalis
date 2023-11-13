import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientList2Component } from './components/client-list/client-list.component';
import { ClientServicesListComponent } from './components/client-services-list/client-services-list.component';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { SharedModule } from '../shared/shared.module';



@NgModule({
  declarations: [
    ClientList2Component,
    ClientServicesListComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    SharedModule
  ]
})
export class ClientModule { }
