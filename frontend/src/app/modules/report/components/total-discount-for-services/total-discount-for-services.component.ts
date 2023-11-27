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
    clientId: 2,
    clientName: 'Taxi SRL',
    service: 'Internet 100MB',
    discount: 2500.0,
    orderNum: '043',
  },
  {
    clientId: 3,
    clientName: 'Pepsi SA',
    service: 'Netflix',
    discount: 250.0,
    orderNum: '021',
  },
  {
    clientId: 4,
    clientName: 'Coca-Cola',
    service: 'Internet 200MB',
    discount: 1200.0,
    orderNum: '002',
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
