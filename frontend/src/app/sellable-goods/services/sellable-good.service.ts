import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { SellableGood } from '../model/sellable-good.model';

type Message = { mensaje: string };

@Injectable({
  providedIn: 'root'
})
export class SellableGoodService {
  private readonly URL: string = 'http://localhost:3000/good';

  private sellableGoodListData: BehaviorSubject<SellableGood[]> = new BehaviorSubject<
    SellableGood[]
  >([]);
  sellableGoodListData$ = this.sellableGoodListData.asObservable();

  constructor(private http: HttpClient) {
    this.loadSellableGoodListData();
  }

  private loadSellableGoodListData() {
    this.getAll().subscribe((goods) => {
      this.sellableGoodListData.next(goods.filter(good => good.active));
    });
  }

  getAll(): Observable<SellableGood[]> {
    return this.http.get<SellableGood[]>(`${this.URL}/getAll`);
  }

  create(sellableGood: SellableGood): Observable<SellableGood> {
    return this.http.post<SellableGood>(
      `${this.URL}/create`,
      sellableGood
    );
  }

  disable(sellableGood: SellableGood): Observable<Message> {    
    return this.http.put<Message>(`${this.URL}/disable`, sellableGood.id);
  }

  edit(sellableGood: SellableGood): Observable<Message> {
    return this.http.put<Message>(`${this.URL}/edit`, sellableGood);
  }

  updateSellableGoodListData() {
    this.loadSellableGoodListData();
  }

}
