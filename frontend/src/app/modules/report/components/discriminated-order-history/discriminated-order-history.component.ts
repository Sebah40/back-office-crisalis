import { Component, OnInit, ViewChild } from '@angular/core';
import { OrderByClient } from '../../DTOs/orderByClientDTO.model';
import { GroupClient } from '../../DTOs/groupData.model';
import { ActivatedRoute } from '@angular/router';
import { ReportService } from '../../service/report.service';
import { PdfService } from '../../service/pdf.service';
import { Location } from '@angular/common';
import { SimpleContainerComponent } from 'src/app/modules/shared/components/simple-container/simple-container.component';

@Component({
  selector: 'app-discriminated-order-history',
  templateUrl: './discriminated-order-history.component.html',
  styleUrls: ['./discriminated-order-history.component.css'],
})
export class DiscriminatedOrderHistoryComponent implements OnInit {
  title = 'Informe de historial de pedidos por cliente';
  @ViewChild(SimpleContainerComponent)
  simpleContainer!: SimpleContainerComponent;

  data: OrderByClient[] = [];

  constructor(
    private reportService: ReportService,
    private route: ActivatedRoute,
    private location: Location,
    private pdfService: PdfService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((param) => {
      const clientId = param['clientId'];
      const dateFrom = param['dateFrom'];
      const dateTo = param['dateTo'];
      this.reportService
        .generateOrderHistory(dateFrom, dateTo, clientId)
        .subscribe({
          next: (res) => {
            if(clientId !== 'null') {
              this.data = res.filter(item => item.clientID == clientId)
            }else{
              this.data = res
            }

          },
        });
    });
  }

  goBack() {
    this.location.back();
  }

  generatePDF() {
    const content = this.simpleContainer.getRootElement();
    this.pdfService.generatePdf(content, 'informe-historial.pdf');
  }

  valores(order: OrderByClient): any[] {
    return Object.values(order);
  }

  group(orders: OrderByClient[]): GroupClient[] {
    return orders.reduce((acc: GroupClient[], order: OrderByClient) => {
      const clientGroup = acc.find(
        (group) => group.client === order.clientName
      );

      if (clientGroup) {
        const goodGroup = clientGroup.items.find(
          (good) => good.sellablegood === order.sellableGood
        );

        if (goodGroup) {
          goodGroup.quantityAccumulator += order.quantity;
          goodGroup.subtotalAccumulator += order.subtotal;
          goodGroup.totalTaxesAccumulator += order.taxes;
          goodGroup.totalAccumulator += order.total;
          goodGroup.discountAccumulator += order.discount;
          goodGroup.items.push({
            orderId: order.orderID,
            status: order.orderStatus,
            date: new Date(order.orderDate),
            quantity: order.quantity,
            price: order.price,
            subtotal: order.subtotal,
            totalTaxes: order.taxes,
            total: order.total,
            warrantyValue: order.warrantyValue,
            supportCharge: order.supportCharge,
            discount: order.discount,
          });
        } else {
          clientGroup.items.push({
            sellablegood: order.sellableGood,
            quantityAccumulator: order.quantity,
            subtotalAccumulator: order.subtotal,
            totalTaxesAccumulator: order.taxes,
            discountAccumulator: order.discount,
            totalAccumulator: order.total,
            items: [
              {
                orderId: order.orderID,
                status: order.orderStatus,
                date: new Date(order.orderDate),
                quantity: order.quantity,
                price: order.price,
                subtotal: order.subtotal,
                totalTaxes: order.taxes,
                total: order.total,
                warrantyValue: order.warrantyValue,
                supportCharge: order.supportCharge,
                discount: order.discount,
              },
            ],
          });
        }
      } else {
        acc.push({
          client: order.clientName,
          items: [
            {
              sellablegood: order.sellableGood,
              quantityAccumulator: order.quantity,
              subtotalAccumulator: order.subtotal,
              totalTaxesAccumulator: order.taxes,
              discountAccumulator: order.discount,
              totalAccumulator: order.total,
              items: [
                {
                  orderId: order.orderID,
                  status: order.orderStatus,
                  date: new Date(order.orderDate),
                  quantity: order.quantity,
                  price: order.price,
                  subtotal: order.subtotal,
                  totalTaxes: order.taxes,
                  total: order.total,
                  warrantyValue: order.warrantyValue,
                  supportCharge: order.supportCharge,
                  discount: order.discount,
                },
              ],
            },
          ],
        });
      }

      return acc;
    }, []);
  }
}
