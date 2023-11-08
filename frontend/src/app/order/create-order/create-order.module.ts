import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';


const routes: Routes = [
  { path: '/order/:id', redirectTo: '/OrderComponent'},
  
];

@NgModule({
  declarations: [

  ],
  imports: [
    RouterModule.forRoot(routes) ,
    CommonModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [ RouterModule ]
})
export class CreateOrderModule { }
