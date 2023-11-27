import { Component, OnInit } from '@angular/core';
import { OrderByClient } from '../../DTOs/orderByClientDTO.model';
import { GroupClient } from '../../DTOs/groupData.model';

@Component({
  selector: 'app-discriminated-order-history',
  templateUrl: './discriminated-order-history.component.html',
  styleUrls: ['./discriminated-order-history.component.css']
})
export class DiscriminatedOrderHistoryComponent implements OnInit {
  title = "Informe de historial de pedidos por cliente"

  data:OrderByClient[] = [
    {
      client: "Coca Cola",
      sellableGood: "Internet 100 MB",
      orderId: 23,
      orderStatus: "Activo",
      date: "01-feb",
      quantity: 1,
      price: 2000,
      subtotal: 2000,
      totalTaxes: 2100,
      total: 10250
    },
    {
      client: "Coca Cola",
      sellableGood: "Celular",
      orderId: 23,
      orderStatus: "Activo",
      date: "01-feb",
      quantity: 2,
      price: 300,
      subtotal: 500,
      totalTaxes: 2100,
      total: 10250
    },
    {
      client: "Coca Cola",
      sellableGood: "Celular",
      orderId: 34,
      orderStatus: "Anulado",
      date: "05-mar",
      quantity: 1,
      price: 350,
      subtotal: 350,
      totalTaxes: 70,
      total: 420
    },
    {
      client: "Pepsi S.a.",
      sellableGood: "Celular",
      orderId: 25,
      orderStatus: "Activo",
      date: "02-feb",
      quantity: 1,
      price: 300,
      subtotal: 300,
      totalTaxes: 60,
      total: 420
    },
    {
      client: "Pepsi S.a.",
      sellableGood: "Internet 200 MB",
      orderId: 26,
      orderStatus: "Activo",
      date: "03-feb",
      quantity: 1,
      price: 2500,
      subtotal: 2500,
      totalTaxes: 2600,
      total: 12550
    }
  ];

  groupedDate = []
  
  ngOnInit(): void {
    console.log("Empiezo!");
    
  }
  
  valores(order:OrderByClient):any[]{
    return Object.values(order);    
  }

  group(orders:OrderByClient[]):GroupClient[]{
    return orders.reduce((acc: GroupClient[], order: OrderByClient) => {
      const clientGroup = acc.find(group => group.client === order.client);
    
      if (clientGroup) {
        const goodGroup = clientGroup.items.find(good => good.sellablegood === order.sellableGood);
    
        if (goodGroup) {
          goodGroup.quantityAccumulator += order.quantity;
          goodGroup.subtotalAccumulator += order.subtotal;
          goodGroup.totalTaxesAccumulator += order.totalTaxes;
          goodGroup.totalAccumulator += order.total;
          goodGroup.items.push({
            orderId: order.orderId,
            status: order.orderStatus,
            date: new Date(order.date),
            quantity: order.quantity,
            price: order.price,
            subtotal: order.subtotal,
            totalTaxes: order.totalTaxes,
            total: order.total
          });
        } else {
          clientGroup.items.push({
            sellablegood: order.sellableGood,
            quantityAccumulator: order.quantity,
            subtotalAccumulator: order.subtotal,
            totalTaxesAccumulator: order.totalTaxes,
            totalAccumulator: order.total,
            items: [{
              orderId: order.orderId,
              status: order.orderStatus,
              date: new Date(order.date),
              quantity: order.quantity,
              price: order.price,
              subtotal: order.subtotal,
              totalTaxes: order.totalTaxes,
              total: order.total
            }]
          });
        }
      } else {
        acc.push({
          client: order.client,
          items: [{
            sellablegood: order.sellableGood,
            quantityAccumulator: order.quantity,
            subtotalAccumulator: order.subtotal,
            totalTaxesAccumulator: order.totalTaxes,
            totalAccumulator: order.total,
            items: [{
              orderId: order.orderId,
              status: order.orderStatus,
              date: new Date(order.date),
              quantity: order.quantity,
              price: order.price,
              subtotal: order.subtotal,
              totalTaxes: order.totalTaxes,
              total: order.total
            }]
          }]
        });
      }
    
      return acc;
    }, []);
  }
}
