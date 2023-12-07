import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { OrderService } from '../service/order.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CalculatedOrder, OrderDetail } from '../model/calculated-order-dto';
import { Client } from 'src/app/modules/client/model/client.model';
import { PersonClient } from 'src/app/modules/client/model/person-client.model';
import { EnterpriseClient } from 'src/app/modules/client/model/enterprise-client.model';
import { SellableGood } from 'src/app/modules/sellable-good/model/sellable-good.model';
import { PdfService } from 'src/app/modules/report/service/pdf.service';
import { SimpleContainerComponent } from 'src/app/modules/shared/components/simple-container/simple-container.component';
import Swal from 'sweetalert2';
import { SweetAlertService } from 'src/app/modules/shared/service/sweet-alert.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css'],
})
export class OrderComponent implements OnInit {
  title = 'Detalle de Pedido';
  order!: CalculatedOrder;
  @ViewChild(SimpleContainerComponent)
  simpleContainer!: SimpleContainerComponent;
  id?: any;

  constructor(
    private orderService: OrderService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private pdfService: PdfService,
    private sweet: SweetAlertService
  ) {}

  getOrder() {
    this.orderService.getOrderWithCalculation(this.id).subscribe((res) => {
      this.order = res;
      this.disable();
    });
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params) => {
      this.id = params['id'];
    });
    this.getOrder();
  }
  disable() {
    var btn = document.getElementById('edit');
    var btn2 = document.getElementById('cancel');
    console.log(this.order);
    if (this.order?.orderState === 'CANCELED') {
      btn?.setAttribute('disabled', '');
      btn2?.setAttribute('disabled', '');
    }
  }
  cancel(id: any) {
    this.orderService.delete(id).subscribe(() => {
      this.getOrder();
      this.sweet.showAlert('Pedido cancelado', 'success');
    });
  }
  goBack() {
    this.router.navigate(['/order/getAll']);
  }

  isPerson(client: Client): client is PersonClient {
    return !('businessName' in client);
  }

  isEnterprise(client: Client): client is EnterpriseClient {
    return 'businessName' in client;
  }

  totalTaxes(sellableGood: SellableGood) {
    return (sellableGood.taxes || [])
      .filter((tax) => tax.active && tax.taxPercentage)
      .reduce((totalImpuestos, tax) => {
        return (
          totalImpuestos +
          (sellableGood.price || 0) * ((tax.taxPercentage ?? 0) / 100)
        );
      }, 0);
  }

  subtotal(orderDetail: OrderDetail): number {
    return (
      (orderDetail.priceSell +
        orderDetail.warrantyValue +
        orderDetail.supportCharge) *
        orderDetail.quantity -
      orderDetail.discount
    );
  }

  clientToData(client: Client): [string, string][] {
    const result: [string, string][] = [
      ['Tipo', ''],
      ['Nombre', ''],
      ['CUIT/DNI', ''],
    ];
    if (this.isPerson(client)) {
      result[0][1] = 'Persona';
      result[1][1] = `${client.lastName} ${client.firstName}`;
      result[2][1] = client.dni;
    } else {
      this.isEnterprise(client);
      result[0][1] = 'Empresa';
      result[1][1] = client.businessName;
      result[2][1] = client.cuit;
    }
    return result;
  }

  generatePDF() {
    const content = this.simpleContainer.getRootElement();
    this.pdfService.generatePdf(content, `pedidoNro${this.order.id}.pdf`);
  }
}
