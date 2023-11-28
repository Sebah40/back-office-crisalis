import { Component, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { ActivatedRoute, Router } from '@angular/router';
import { ReportService } from '../../service/report.service';
import { Location } from '@angular/common';
import { jsPDF } from 'jspdf';
import html2canvas from 'html2canvas';

@Component({
  selector: 'app-biggest-discount',
  templateUrl: './biggest-discount.component.html',
  styleUrls: ['./biggest-discount.component.css'],
})
export class BiggestDiscountComponent {
  dataSource!: any;
  biggestDiscountList!: any;
  displayedColumns: string[] = ['clientName', 'service', 'discount', 'orderID'];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild('pdfTable') pdfTable!: ElementRef;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private reportService: ReportService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((param) => {
      const clientId = param['clientId'];
      const dateFrom = param['dateFrom'];
      const dateTo = param['dateTo'];

      this.reportService
        .generateBiggestDiscountList(dateFrom, dateTo)
        .subscribe({
          next: (res) => {
            console.log(res);
            this.biggestDiscountList = res;
            console.log(this.biggestDiscountList);
            if (clientId === 'null') {
              this.dataSource = new MatTableDataSource<any>(
                this.biggestDiscountList
              );
            } else {
              this.dataSource = new MatTableDataSource<any>(
                this.biggestDiscountList.filter(
                  (e: any) => e.clientID == clientId
                )
              );
            }
            this.dataSource.sort = this.sort;
          },
        });
    });
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  getColumnHeader(column: string): string {
    switch (column) {
      case 'clientName':
        return 'Cliente';
      case 'service':
        return 'Servicio';
      case 'orderID':
        return 'Número de Pedido';
      case 'discount':
        return 'Mayor Descuento';
      default:
        return column;
    }
  }

  goBack() {
    this.location.back();
  }

  generatePDF() {
    const content = this.pdfTable.nativeElement;

    const options = {
      scale: 2.5,
    };

    html2canvas(content, options).then((canvas) => {
      const imgData = canvas.toDataURL('image/jpeg');
      const pdf = new jsPDF('p', 'mm', 'a4');
      const imgWidth = 210;
      const imgHeight = (canvas.height * imgWidth) / canvas.width;

      pdf.addImage(imgData, 'JPEG', 0, 20, imgWidth, imgHeight);
      pdf.save('informe-servicio.pdf');
    });
  }
}
