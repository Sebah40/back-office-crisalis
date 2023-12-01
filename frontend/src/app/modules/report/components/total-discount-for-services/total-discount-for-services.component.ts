import { OrderByClient } from '../../DTOs/orderByClientDTO.model';

import { DataForTotalDiscount } from '../../DTOs/dataForTotalDiscount.model';
import { GroupClientForDiscount } from '../../DTOs/groupDataTotalDiscount.model';
import { Location } from '@angular/common';

import { Component, ElementRef, ViewChild } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';
import { ReportService } from '../../service/report.service';
import { PdfService } from '../../service/pdf.service';
import { SimpleContainerComponent } from 'src/app/modules/shared/components/simple-container/simple-container.component';

@Component({
  selector: 'app-total-discount-for-services',
  templateUrl: './total-discount-for-services.component.html',
  styleUrls: ['./total-discount-for-services.component.css'],
})
export class TotalDiscountForServicesComponent {
  title = 'Informe de descuentos totales en pedidos por cliente';
  data: DataForTotalDiscount[] = [];
  @ViewChild(SimpleContainerComponent)
  simpleContainer!: SimpleContainerComponent;

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private reportService: ReportService,
    private pdfService: PdfService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((param) => {
      const clientId = param['clientId'];
      const dateFrom = param['dateFrom'];
      const dateTo = param['dateTo'];

      this.reportService
        .generateHistoryTotalDiscount(dateFrom, dateTo)
        .subscribe({
          next: (res) => {
            if (clientId !== 'null') {
              this.data = res.filter((e) => e.clientId === clientId);
            } else {
              this.data = res;
            }
          },
        });

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

  generatePDF() {
    const content = this.simpleContainer.getRootElement();
    this.pdfService.generatePdf(
      content,
      'informe-descuentos-totales-por-servicio.pdf'
    );
  }
}
