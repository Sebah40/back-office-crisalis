import { Injectable } from '@angular/core';
import { IEnterpriseGet } from '../model/enterpriseGet.model';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { ResponseCreateUser } from 'src/app/modules/user/interfaces/ResponseCreateUser.type';
import { IEnterprise } from '../model/enterprise.model';

type Enterprise = { id: number };
type Message = { mensaje: string };

@Injectable({
  providedIn: 'root',
})
export class EnterpriseService {
  private readonly URL: string = 'http://localhost:3000/enterprise';

  private enterpriseListData: BehaviorSubject<IEnterpriseGet[]> =
    new BehaviorSubject<IEnterpriseGet[]>([]);
  enterpriseListData$ = this.enterpriseListData.asObservable();

  constructor(private http: HttpClient) {
    this.loadEnterpriseListData(); // Cargar la lista de empresas al iniciar el servicio
  }

  // Cargar la lista de empresas
  private loadEnterpriseListData() {
    this.getAll().subscribe((enterprises) => {
      this.enterpriseListData.next(enterprises);
    });
  }

  getAll(): Observable<IEnterpriseGet[]> {
    return this.http.get<IEnterpriseGet[]>(`${this.URL}/getAll`);
  }

  create(enterprise: IEnterprise): Observable<ResponseCreateUser> {
    return this.http.post<ResponseCreateUser>(`${this.URL}/create`, enterprise);
  }

  delete(id: Enterprise): Observable<Message> {
    console.log(id);
    return this.http.post<Message>(`${this.URL}/disable`, id);
  }

  edit(enterprise: IEnterprise): Observable<ResponseCreateUser> {
    return this.http.post<ResponseCreateUser>(`${this.URL}/edit`, enterprise);
  }

  // Actualizar la lista de empresas
  updateEnterpriseListData() {
    this.loadEnterpriseListData();
  }
}
