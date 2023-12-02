import { Component, OnInit } from '@angular/core';
import { OrderDTO } from '../model/order-dto';
import { OrderService } from '../service/order.service';
import { RouterModule, Routes, Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css'],
})
export class OrderListComponent implements OnInit {
  //order = new OrderDTO(300, 2, new Date());

  constructor(private orderService: OrderService, private router: Router) {}

  orderList: OrderDTO[] = [];
  loadOrderList(): void {
    this.orderService.orderList().subscribe(
      (orderList) => {
        this.orderList = orderList;
        console.log(this.orderList);
      },
      (err) => {
        console.log(err);
      }
    );
  }

  delete(id: any): void {
    this.orderService.delete(id).subscribe();
  }
  validate(id: any) {
    this.orderService.validate(id).subscribe((res) => res);

    Swal.fire('Órden validada', undefined, 'success');
  }

  redirect(id: any): void {
    this.router.navigate(['/' + id]);
  }

  ngOnInit(): void {
    this.loadOrderList();
  }

  headers = ['N° de pedido', 'Cliente', 'Fecha', 'Estado'];
  entityKeys = ['N° de pedido', 'Responsable', 'Fecha', 'Total'];
  title: string = 'pedidos';

  entityKeyToRedirect: string = '';
  pathCreateEntity: string = '';
  pathEditEntity: string = '';
  buttonText: string = '';
  isEditable: boolean = false;
  deleteEntity?: (entity: any) => void;
}
