import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListTableComponent } from './components/list-table/list-table.component';
import { SimpleContainerComponent } from './components/simple-container/simple-container.component';



@NgModule({
  declarations: [
    ListTableComponent,
    SimpleContainerComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    ListTableComponent,
    SimpleContainerComponent
  ]
})
export class SharedModule { }
