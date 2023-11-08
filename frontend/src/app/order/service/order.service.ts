import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { OrderDto } from '../order-dto';
import { RequestBodyCreateOrderDto } from '../request-body-create-order-dto';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  orderURL = 'http://localhost:3000/order'
  constructor(private httpClient: HttpClient) { }

  public orderList(): Observable<OrderDto[]> {
    return this.httpClient.get<OrderDto[]>(this.orderURL+'/getAll').pipe(map(res => res))
  }
  public getOrder(id: any): Observable<OrderDto> {
    return this.httpClient.get<OrderDto>(this.orderURL + `/${id}`).pipe(map(res => res))
  }

  public save(order: RequestBodyCreateOrderDto): Observable<any> {
    console.log(order);
    return this.httpClient.post<any>(this.orderURL + '/create', order).pipe(map(res => res));
  }

  public update(order: OrderDto, id: number): Observable<any> {
    return this.httpClient.put<any>(this.orderURL + `/update/${id}`, order).pipe(map(res => res));
  }

  public delete(id: number): Observable<any> {
    return this.httpClient.put<any>(this.orderURL + `/cancel/${id}`, id).pipe(map(res => res));
  }
}
