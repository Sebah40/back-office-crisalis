import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SellableGoodsRoutingModule } from './sellable-goods-routing.module';
import { SellableGoodsListComponent } from './components/sellable-goods-list/sellable-goods-list.component';
import { SellableGoodFormComponent } from './components/sellable-good-form/sellable-good-form.component';
import { FormatTaxesPipe } from './pipes/format-taxes.pipe';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    SellableGoodsListComponent,
    SellableGoodFormComponent,
    FormatTaxesPipe,
  ],
  imports: [
    CommonModule,
    SellableGoodsRoutingModule,
    FormsModule, ReactiveFormsModule
  ]
})
export class SellableGoodsModule { }
