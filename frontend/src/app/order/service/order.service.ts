import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { OrderDTO } from '../model/order-dto';
import { RequestBodyCreateOrderDto } from '../model/request-body-create-order-dto';
import { CalculatedOrder } from '../model/calculated-order-dto';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  orderURL = 'http://localhost:3000/order'
  constructor(private httpClient: HttpClient) { }

  public orderList(): Observable<OrderDTO[]> {
    return this.httpClient.get<OrderDTO[]>(this.orderURL+'/getAll').pipe(map(res => res))
  }
  public getOrder(id: any): Observable<OrderDTO> {
    return this.httpClient.get<any>(this.orderURL + `/${id}`).pipe(map(res => res))
  }

  public getOrderWithCalculation(id:number): Observable<CalculatedOrder> {
    return this.httpClient.get<any>(`${this.orderURL}/withcalculation/${id}`).pipe(map(res => res))
  }

  public save(order: RequestBodyCreateOrderDto): Observable<any> {
    console.log(order);
    return this.httpClient.post<any>(this.orderURL + '/create', order).pipe(map(res => res));
  }

  public update(order: any): Observable<any> {
    return this.httpClient.put<any>(this.orderURL + `/edit`, order).pipe(map(res => res));
  }

  public delete(id: any): Observable<any> {
    return this.httpClient.delete<any>(this.orderURL + `/cancel/${id}`, {}).pipe(map(res => res));
  }

  public validate(id: any): Observable<any> {
    return this.httpClient.put<any>(this.orderURL + `/validate/${id}`, {}).pipe(map(res => res));
  }
}
