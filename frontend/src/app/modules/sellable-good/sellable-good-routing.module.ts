import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SellableGoodsListComponent } from './components/sellable-goods-list/sellable-goods-list.component';
import { SellableGoodFormComponent } from './components/sellable-good-form/sellable-good-form.component';
import { userGuard } from 'src/app/guards/user.guard';

const routes: Routes = [
  {
    path: 'good',
    component: SellableGoodsListComponent,
    canActivate: [userGuard],
  },
  {
    path: 'good/create',
    component: SellableGoodFormComponent,
    canActivate: [userGuard],
  },
  {
    path: 'good/edit',
    component: SellableGoodFormComponent,
    canActivate: [userGuard],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SellableGoodsRoutingModule {}
