import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormReportsComponent } from './components/form-reports/form-reports.component';

const routes: Routes = [{ path: 'report', component: FormReportsComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ReportRoutingModule {}
