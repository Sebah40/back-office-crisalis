import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SellableGoodsRoutingModule } from './sellable-good-routing.module';
import { SellableGoodsListComponent } from './components/sellable-goods-list/sellable-goods-list.component';
import { SellableGoodFormComponent } from './components/sellable-good-form/sellable-good-form.component';
import { FormatTaxesPipe } from './pipes/format-taxes.pipe';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TaxFormatterPipe } from './pipes/tax-formatter.pipe';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [
    SellableGoodsListComponent,
    SellableGoodFormComponent,
    FormatTaxesPipe,
    TaxFormatterPipe,
  ],
  imports: [
    CommonModule,
    SellableGoodsRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
  ],
})
export class SellableGoodsModule {}
