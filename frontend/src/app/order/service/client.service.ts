import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { ClientEntity } from '../model/client-entity';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  orderURL = 'http://localhost:3000/client'
  constructor(private httpClient: HttpClient) { }

  public searchbar(query: string): Observable<any> {
    return this.httpClient.get<any>(this.orderURL + `/getByAnyParameter?query=${query}`).pipe(map(res => res));
  }

  public getClients(): Observable<any> {
    return this.httpClient.get<any[]>(this.orderURL + '/getAll' ).pipe(map(res => res));
  }

  public getClient(id: any): Observable<ClientEntity> {
    return this.httpClient.get<any>(this.orderURL + '/id' ).pipe(map(res => res));
  }
}
