import { Component, OnInit } from '@angular/core';
import { OrderByClient } from '../../DTOs/orderByClientDTO.model';
import { GroupClient } from '../../DTOs/groupData.model';
import { ActivatedRoute } from '@angular/router';
import { ReportService } from '../../service/report.service';

@Component({
  selector: 'app-discriminated-order-history',
  templateUrl: './discriminated-order-history.component.html',
  styleUrls: ['./discriminated-order-history.component.css']
})
export class DiscriminatedOrderHistoryComponent implements OnInit {
  title = "Informe de historial de pedidos por cliente"

  data:OrderByClient[] = [
    {
      clientName: "Coca Cola",
      sellableGood: "Internet 100 MB",
      orderID: 23,
      orderStatus: "Activo",
      orderDate: "01-feb",
      quantity: 1,
      price: 2000,
      subtotal: 2000,
      taxes: 2100,
      total: 10250,
      discount: 0,
      clientID: 1,
    },
    {
      clientName: "Coca Cola",
      sellableGood: "Celular",
      orderID: 23,
      orderStatus: "Activo",
      orderDate: "01-feb",
      quantity: 2,
      price: 300,
      subtotal: 500,
      taxes: 2100,
      total: 10250,
      discount: 0,
      clientID: 1,
    },
    {
      clientName: "Coca Cola",
      sellableGood: "Celular",
      orderID: 34,
      orderStatus: "Anulado",
      orderDate: "05-mar",
      quantity: 1,
      price: 350,
      subtotal: 350,
      taxes: 70,
      total: 420,
      discount: 0,
      clientID: 1,
    },
    {
      clientName: "Pepsi S.a.",
      sellableGood: "Celular",
      orderID: 25,
      orderStatus: "Activo",
      orderDate: "02-feb",
      quantity: 1,
      price: 300,
      subtotal: 300,
      taxes: 60,
      total: 420,
      discount: 0,
      clientID: 1,
    },
    {
      clientName: "Pepsi S.a.",
      sellableGood: "Internet 200 MB",
      orderID: 26,
      orderStatus: "Activo",
      orderDate: "03-feb",
      quantity: 1,
      price: 2500,
      subtotal: 2500,
      taxes: 2600,
      total: 12550,
      discount: 0,
      clientID: 1,
    }
  ];
  
  constructor(private reportService: ReportService,private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((param) => {
      const clientId = param['clientId'];
      const dateFrom = param['dateFrom']
      const dateTo = param['dateTo']
      this.reportService
        .generateOrderHistory(dateFrom, dateTo, clientId)
        .subscribe({
          next: (res) => {
            this.data = res;
          },
        });
    });
    }
  
  valores(order:OrderByClient):any[]{
    return Object.values(order);    
  }

  group(orders:OrderByClient[]):GroupClient[]{
    return orders.reduce((acc: GroupClient[], order: OrderByClient) => {
      const clientGroup = acc.find(group => group.client === order.clientName);
    
      if (clientGroup) {
        const goodGroup = clientGroup.items.find(good => good.sellablegood === order.sellableGood);
    
        if (goodGroup) {
          goodGroup.quantityAccumulator += order.quantity;
          goodGroup.subtotalAccumulator += order.subtotal;
          goodGroup.totalTaxesAccumulator += order.taxes;
          goodGroup.totalAccumulator += order.total;
          goodGroup.items.push({
            orderId: order.orderID,
            status: order.orderStatus,
            date: new Date(order.orderDate),
            quantity: order.quantity,
            price: order.price,
            subtotal: order.subtotal,
            totalTaxes: order.taxes,
            total: order.total
          });
        } else {
          clientGroup.items.push({
            sellablegood: order.sellableGood,
            quantityAccumulator: order.quantity,
            subtotalAccumulator: order.subtotal,
            totalTaxesAccumulator: order.taxes,
            totalAccumulator: order.total,
            items: [{
              orderId: order.orderID,
              status: order.orderStatus,
              date: new Date(order.orderDate),
              quantity: order.quantity,
              price: order.price,
              subtotal: order.subtotal,
              totalTaxes: order.taxes,
              total: order.total
            }]
          });
        }
      } else {
        acc.push({
          client: order.clientName,
          items: [{
            sellablegood: order.sellableGood,
            quantityAccumulator: order.quantity,
            subtotalAccumulator: order.subtotal,
            totalTaxesAccumulator: order.taxes,
            totalAccumulator: order.total,
            items: [{
              orderId: order.orderID,
              status: order.orderStatus,
              date: new Date(order.orderDate),
              quantity: order.quantity,
              price: order.price,
              subtotal: order.subtotal,
              totalTaxes: order.taxes,
              total: order.total
            }]
          }]
        });
      }
    
      return acc;
    }, []);
  }
}
