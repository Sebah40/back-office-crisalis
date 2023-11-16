import { Component, Input } from '@angular/core';
import { OrderDTO } from '../model/order-dto';

@Component({
  selector: 'app-list-table',
  templateUrl: './list-table.component.html',
  styleUrls: ['./list-table.component.css']
})
export class ListTableComponent {
  @Input() headers:any;
  @Input() orderList?: OrderDTO[]
}
