import { Component, OnInit } from '@angular/core';
import { OrderService } from '../service/order.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CalculatedOrder } from '../model/calculated-order-dto';
import { Client } from 'src/app/modules/client/model/client.model';
import { PersonClient } from 'src/app/modules/client/model/person-client.model';
import { EnterpriseClient } from 'src/app/modules/client/model/enterprise-client.model';


@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  order!:CalculatedOrder;
  id?:any;

  constructor(private orderService: OrderService,
    private activatedRoute: ActivatedRoute, private router: Router) {

    }

  getOrder() {
    this.orderService.getOrderWithCalculation(this.id).subscribe(res => {
      this.order = res;
      this.disable();
    })
  }

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
  goBack(){
    this.router.navigate(['/order/getAll']);
  }

  isPerson(client: Client): client is PersonClient {
    return !('businessName' in client);
  }

  isEnterprise(client: Client): client is EnterpriseClient {
    return 'businessName' in client;
  }
}
