import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListTableComponent } from './components/list-table/list-table.component';
import { SimpleContainerComponent } from './components/simple-container/simple-container.component';
import { ButtonBackComponent } from './components/button-back/button-back.component';

@NgModule({
  declarations: [
    ListTableComponent,
    SimpleContainerComponent,
    ButtonBackComponent,
  ],
  imports: [CommonModule],
  exports: [ListTableComponent, SimpleContainerComponent, ButtonBackComponent],
})
export class SharedModule {}
