import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormReportsComponent } from './components/form-reports/form-reports.component';
import { BiggestDiscountComponent } from './components/biggest-discount/biggest-discount.component';
import { TotalDiscountForServicesComponent } from './components/total-discount-for-services/total-discount-for-services.component';
import { DiscriminatedOrderHistoryComponent } from './components/discriminated-order-history/discriminated-order-history.component';

const routes: Routes = [
  { path: 'report', component: FormReportsComponent },
  { path: 'report/biggest-discount', component: BiggestDiscountComponent },
  {
    path: 'report/discrimimated-order-history',
    component: DiscriminatedOrderHistoryComponent,
  },
  {
    path: 'report/total-discount-for-services',
    component: TotalDiscountForServicesComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ReportRoutingModule {}
