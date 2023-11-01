import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SellableGoodsRoutingModule } from './sellable-goods-routing.module';
import { SellableGoodsListComponent } from './components/sellable-goods-list/sellable-goods-list.component';
import { SellableGoodFormComponent } from './components/sellable-good-form/sellable-good-form.component';


@NgModule({
  declarations: [
    SellableGoodsListComponent,
    SellableGoodFormComponent
  ],
  imports: [
    CommonModule,
    SellableGoodsRoutingModule
  ]
})
export class SellableGoodsModule { }
