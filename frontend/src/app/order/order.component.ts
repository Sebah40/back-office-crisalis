import { Component, OnInit } from '@angular/core';
import  { OrderDto } from './order-dto';
import { OrderService } from './service/order.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ClientEntity } from './client-entity';


@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  order?:OrderDto;

  constructor(private orderService: OrderService,
    private activatedRoute: ActivatedRoute, private router: Router) {
  
    }

  loadOrder(id: any): any {
    this.orderService.getOrder(id).subscribe(res => {
      this.order = res;
      return this.order;
    })
  }
  id?:any;
  ngOnInit(): void {
    
    this.activatedRoute.params.subscribe((params) => {
      this.id = params['id'];
    })
    this.orderService.getOrder(this.id).subscribe(res => {
      this.order = new OrderDto(res.client, res.orderState, res.dateCreated, res.orderDetailList);
      console.log(this.order)
      return this.order;
      

    })
    //this.order = this.loadOrder(this.id);
    console.log(this.order)
  }

}
