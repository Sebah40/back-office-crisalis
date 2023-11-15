import { Component, OnInit } from '@angular/core';
import  { OrderDTO } from './order-dto';
import { OrderService } from './service/order.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ClientEntity } from './client-entity';


@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  order?:OrderDTO;

  constructor(private orderService: OrderService,
    private activatedRoute: ActivatedRoute, private router: Router) {

    }

  getOrder() {
    this.orderService.getOrder(this.id).subscribe(res => {
      this.order = new OrderDTO(this.id, res.client, res.orderState, res.dateCreated, res.orderDetailDTOList);
      this.disable();
    })
  }
  id?:any;
  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params) => {
      this.id = params['id'];
    })
    this.getOrder();
  }
  disable() {
    var btn = document.getElementById('edit');
    var btn2 = document.getElementById('cancel');
    console.log(this.order)
    if(this.order?.orderState === "CANCELED") {
      btn?.setAttribute('disabled', '');
      btn2?.setAttribute('disabled', '');
    }
  }
  cancel(id: any) {
    this.orderService.delete(id).subscribe(res => res);
    this.getOrder();
  }

  validate(id: any) {
    this.orderService.validate(id).subscribe(res => res);
    this.getOrder();
  }

}
