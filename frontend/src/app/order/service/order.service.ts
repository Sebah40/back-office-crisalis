import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map, BehaviorSubject } from 'rxjs';
import { OrderDTO } from '../model/order-dto';
import { RequestBodyCreateOrderDto } from '../model/request-body-create-order-dto';
import { CalculatedOrder } from '../model/calculated-order-dto';
import { IOrderGet } from '../model/order-get';
@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private orderListData: BehaviorSubject<IOrderGet[]> = new BehaviorSubject<
  IOrderGet[]
>([]);
 orderListData$ = this.orderListData.asObservable();
  orderURL = 'http://localhost:3000/order'

  constructor(private httpClient: HttpClient) { 
    this.loadOrderListData();
  }

  public orderList(): Observable<OrderDTO[]> {
    return this.httpClient.get<OrderDTO[]>(this.orderURL+'/getAll').pipe(map(res => res))
  }
  public loadOrderListData() {

    this.getAll().subscribe((data) => {
      this.orderListData.next(data);
      console.log("orderListData Actualizado")
    });
  }
  getAll(): Observable<IOrderGet[]> {
    return this.httpClient.get<IOrderGet[]>(this.orderURL+'/getAll');
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

  updateOrderListData() {
    console.log("Actualizando OrderListData")
    this.loadOrderListData();
  }
}
