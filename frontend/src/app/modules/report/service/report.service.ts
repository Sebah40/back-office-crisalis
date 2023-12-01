import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrderByClient } from '../DTOs/orderByClientDTO.model';
import { DataForTotalDiscount } from '../DTOs/dataForTotalDiscount.model';

@Injectable({
  providedIn: 'root',
})
export class ReportService {
  private readonly URL: string = 'http://localhost:3000/client';

  constructor(private http: HttpClient) {}

  generateBiggestDiscountList(
    dateFrom: string,
    dateTo: string
  ): Observable<any> {
    return this.http.get<any>(
      `${this.URL}/maxDiscountServicesInRange?${
        dateFrom !== '' ? 'startDate=' + dateFrom + '&' : ''
      }${dateTo !== '' ? 'endDate=' + dateTo : ''}`
    );
  }

  generateOrderHistory(
    dateFrom: string,
    dateTo: string,
    clientId: string
  ): Observable<OrderByClient[]> {
    const urlRequest = `http://localhost:3000/order/filteredReport?startDate=${dateFrom}&endDate=${dateTo}`;
    return this.http.get<OrderByClient[]>(urlRequest);
  }
  generateHistoryTotalDiscount(
    dateFrom: string = '',
    dateTo: string = ''
  ): Observable<DataForTotalDiscount[]> {
    const urlRequest = `http://localhost:3000/report/total-discount`;
    return this.http.get<DataForTotalDiscount[]>(urlRequest, {
      params: { fromDate: dateFrom, untilDate: dateTo },
    });
  }
}
