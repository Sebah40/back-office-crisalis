import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormReportsComponent } from './components/form-reports/form-reports.component';
import { ReportRoutingModule } from './report.routing.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [FormReportsComponent],
  imports: [CommonModule, ReportRoutingModule, FormsModule],
  exports: [FormReportsComponent],
})
export class ReportModule {}
