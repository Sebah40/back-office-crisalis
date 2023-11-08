import { Component, Input } from '@angular/core';
import { OrderDto } from '../order-dto';

@Component({
  selector: 'app-list-table',
  templateUrl: './list-table.component.html',
  styleUrls: ['./list-table.component.css']
})
export class ListTableComponent {
  @Input() headers:any;
  @Input() orderList?: OrderDto[]
}
