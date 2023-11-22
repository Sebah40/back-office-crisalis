import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ListTableComponent } from '../list-table/list-table.component';



@NgModule({
  declarations: [
    ListTableComponent,
  ],
  imports: [
    CommonModule,
    RouterModule
  ]
})
export class OrderListModule { }
