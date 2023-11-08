import { Component, OnInit } from '@angular/core';
import  { OrderDto } from '../order-dto';
import { OrderService } from '../service/order.service';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {



  //order = new OrderDto(300, 2, new Date());


  constructor(private orderService: OrderService) {}

  orderList: OrderDto[] = [];
  loadOrderList(): void {
      this.orderService.orderList().subscribe(orderList => {
        this.orderList = orderList;
        console.log(this.orderList);
      }, err => {
        console.log(err);
      })
  }

  delete(id: any): void {
    this.orderService.delete(id).subscribe();
  }

  ngOnInit(): void {
    this.loadOrderList();
  }

  headers = ['N° de pedido', 'Responsable', 'Fecha', 'Total', ''];
  entityKeys = ['N° de pedido', 'Responsable', 'Fecha', 'Total'];
  title: string = 'pedidos';


  entityKeyToRedirect: string = '';
  pathCreateEntity: string = '';
  pathEditEntity: string = '';
  buttonText: string = '';
  isEditable: boolean = false;
  deleteEntity?: (entity: any) => void;

}
