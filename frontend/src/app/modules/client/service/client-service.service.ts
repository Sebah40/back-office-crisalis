import { Injectable } from '@angular/core';
import { Client } from '../model/client.model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { SellableGood } from '../../sellable-good/model/sellable-good.model';

@Injectable({
  providedIn: 'root'
})
export class ClientServiceService {

  URL = 'http://localhost:3000/client';

  constructor(private http: HttpClient) { }

  getAll(): Observable<Client[]> {
    return this.http.get<Client[]>(`${this.URL}/getAll`);
  }

  getClientServices(id:number):Observable<SellableGood[]> {
    return this.http.get<SellableGood[]>(`${this.URL}/getServices/${id}`);
  }

  removeActiveService(clientId:number, serviceId:number):Observable<string> {
    return this.http.post(`${this.URL}/${clientId}/removeActiveService/${serviceId}`,null, {responseType:'text'});
  }
}
