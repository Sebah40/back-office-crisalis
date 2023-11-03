import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SellableGoodsListComponent } from './components/sellable-goods-list/sellable-goods-list.component';
import { SellableGoodFormComponent } from './components/sellable-good-form/sellable-good-form.component';

const routes: Routes = [
  {
    path: 'good',
    component: SellableGoodsListComponent,
  },
  {
    path: 'good/create',
    component: SellableGoodFormComponent,
  },
  {
    path: 'good/edit',
    component: SellableGoodFormComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SellableGoodsRoutingModule {}
