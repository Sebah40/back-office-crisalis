import { Component, OnInit } from '@angular/core';
import { OrderDTO } from '../model/order-dto';
import { OrderService } from '../service/order.service';
import { RouterModule, Routes, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { delay } from 'rxjs';
import { SweetAlertService } from 'src/app/modules/shared/service/sweet-alert.service';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css'],
})
export class OrderListComponent implements OnInit {
  //order = new OrderDTO(300, 2, new Date());

  constructor(
    private orderService: OrderService,
    private router: Router,
    private sweet: SweetAlertService
  ) {}

  orderList: OrderDTO[] = [];

  public delete(id: any): void {
    this.orderService
      .delete(id)
      .pipe()
      .subscribe((res) => {
        this.orderService.updateOrderListData();
        Swal.fire('Órden borrada', undefined, 'success');
      });
  }
  validate(id: any) {
    this.orderService
      .validate(id)
      .pipe()
      .subscribe((res) => {
        this.orderService.updateOrderListData();
        this.sweet.showAlert('Órden validada', 'success');
      });
  }

  redirect(id: any): void {
    this.router.navigate(['/' + id]);
  }

  ngOnInit(): void {
    this.orderService.updateOrderListData();
    this.orderService.orderListData$.subscribe((data) => {
      this.orderList = data;
    });

    this.orderService.getAll().subscribe((data) => {
      this.orderList = data;
    });
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
