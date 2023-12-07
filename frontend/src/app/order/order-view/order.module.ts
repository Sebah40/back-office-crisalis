import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditOrderComponent } from '../edit-order/edit-order.component';
import { SharedModule } from 'src/app/modules/shared/shared.module';
import { ReportModule } from 'src/app/modules/report/report.module';

//import { OrderRoutingModule } from './order-routing.module';



@NgModule({
  declarations: [
  ],
  imports: [
    CommonModule,
    SharedModule,
    ReportModule
    //OrderRoutingModule
  ]
})
export class OrderModule { }
