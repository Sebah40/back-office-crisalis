
import { Component, OnInit } from '@angular/core';
import { OrderByClient } from '../../DTOs/orderByClientDTO.model';
import { GroupClient } from '../../DTOs/groupData.model';
import { ActivatedRoute } from '@angular/router';
import { DataForTotalDiscount } from '../../DTOs/dataForTotalDiscount.model';
import { GroupClientForDiscount } from '../../DTOs/groupDataTotalDiscount.model';
import { Location } from '@angular/common';

import { Component, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { ActivatedRoute, Router } from '@angular/router';

const DATA: any[] = [
  {
    clientId: 1,
    clientName: 'Gonzalo Fleitas',
    service: 'Linea celular',
    discount: 900.0,
    orderNum: '001',
  },
  {
    clientId: 1,
    clientName: 'Gonzalo Fleitas',
    service: 'Linea celular',
    discount: 230.0,
    orderNum: '061',
  },
  {
    clientId: 1,
    clientName: 'Gonzalo Fleitas',
    service: 'Internet 100MB',
    discount: 540.0,
    orderNum: '044',
  },
  {
    clientId: 4,
    clientName: 'Coca-Cola',
    service: 'Internet 200MB',
    discount: 1200.0,
    orderNum: '002',
  },
];


const data2 = [
  {
    clientId: 1, // esto no se muestra
    clientName: 'Fede',
    totalDiscount: 222,
    report: [
      {
        discount: 111,
        orderNum: '011',
        service: 'Linea Celular',
      },
      {
        discount: 111,
        orderNum: '011',
        service: 'Linea Celular',
      },
      {
        discount: 111,
        orderNum: '011',
        service: 'Linea Celular',
      },
    ],
  },
  {
    clientId: 1, // esto no se muestra
    clientName: 'Gonza',
    totalDiscount: 222,
    report: [
      {
        discount: 111,
        orderNum: '011',
        service: 'Linea Celular',
      },
      {
        discount: 111,
        orderNum: '011',
        service: 'Linea Celular',
      },
      {
        discount: 111,
        orderNum: '011',
        service: 'Linea Celular',
      },
    ],
  },
];

@Component({
  selector: 'app-total-discount-for-services',
  templateUrl: './total-discount-for-services.component.html',
  styleUrls: ['./total-discount-for-services.component.css'],
})
export class TotalDiscountForServicesComponent {
  title = 'Informe de descuentos totales en pedidos por cliente';

  data: DataForTotalDiscount[] = [
    {
      clientId: 1,
      client: 'Coca Cola',
      service: 'Internet 100 MB',
      orderNum: 23,
      date: '01-feb',
      discount: 1200,
    },
    {
      clientId: 1,
      client: 'Coca Cola',
      service: 'Internet 100 MB',
      orderNum: 11,
      date: '22-feb',
      discount: 900,
    },
    {
      clientId: 2,
      client: 'Pepsi',
      service: 'Internet 100 MB',
      orderNum: 44,
      date: '25-feb',
      discount: 1900,
    },
    {
      clientId: 1,
      client: 'Coca Cola',
      service: 'Linea Celular',
      orderNum: 45,
      date: '27-feb',
      discount: 200,
    },
  ];

  constructor(private route: ActivatedRoute, private location: Location) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((param) => {
      const clientId = param['clientId'];

      if (clientId !== 'null') {
        this.data = this.data.filter((element) => element.clientId == clientId);
      }
      const dateFrom = param['dateFrom'];
      const dateTo = param['dateTo'];
      console.log(clientId);
    });
  }

  valores(order: OrderByClient): any[] {
    return Object.values(order);
  }

  group(orders: DataForTotalDiscount[]): GroupClientForDiscount[] {
    return orders.reduce(
      (acc: GroupClientForDiscount[], order: DataForTotalDiscount) => {
        const clientGroup = acc.find((group) => group.client === order.client);

        if (clientGroup) {
          const serviceGroup = clientGroup.services.find(
            (serv) => serv.service === order.service
          );

          if (serviceGroup) {
            serviceGroup.totalDiscount += order.discount;

            serviceGroup.items.push({
              orderNum: order.orderNum,
              discount: order.discount,
              date: new Date(order.date),
            });
          } else {
            clientGroup.services.push({
              service: order.service,
              totalDiscount: order.discount,
              items: [
                {
                  orderNum: order.orderNum,
                  date: new Date(order.date),
                  discount: order.discount,
                },
              ],
            });
          }
        } else {
          acc.push({
            client: order.client,
            services: [
              {
                service: order.service,
                totalDiscount: order.discount,
                items: [
                  {
                    orderNum: order.orderNum,
                    date: new Date(order.date),
                    discount: order.discount,
                  },
                ],
              },
            ],
          });
        }

        return acc;
      },
      []
    );
  }

  goBack() {
    this.location.back();
  }
}
