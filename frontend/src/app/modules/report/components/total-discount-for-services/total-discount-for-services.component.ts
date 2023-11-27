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
  dataSource!: any;
  displayedColumns: string[] = [
    'clientName',
    'service',
    'discount',
    'orderNum',
  ];
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((param) => {
      const clientId = param['clientId'];
      console.log(clientId);
      if (clientId === 'null') {
        this.dataSource = new MatTableDataSource<any>(DATA);
      } else {
        this.dataSource = new MatTableDataSource<any>(
          DATA.filter((e) => e.clientId == clientId)
        );
      }
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
      case 'orderNum':
        return 'Número de Pedido';
      case 'discount':
        return 'Mayor Descuento';
      default:
        return column;
    }
  }
}
