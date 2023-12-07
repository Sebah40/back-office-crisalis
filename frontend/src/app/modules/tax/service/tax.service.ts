import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { Tax } from '../model/tax';
import { ITaxGet } from '../model/tax-get';
import { ResponseCreate } from 'src/app/components/interfaces/ResponseCreate.type';
type ITax = { id: number };

type Message = { mensaje: string };

@Injectable({
  providedIn: 'root',
})
export class TaxService {
  private apiUrl = 'http://localhost:3000/tax';

  private taxListData: BehaviorSubject<ITaxGet[]> = new BehaviorSubject<
  ITaxGet[]
>([]);
 taxListData$ = this.taxListData.asObservable();

  constructor(private http: HttpClient) {
    this.loadTaxListData();
  }


  private loadTaxListData() {
    
    this.getAll().subscribe((data) => {
      this.taxListData.next(data);
      console.log("taxListData Actualizado")
    });
  }


  getAll(): Observable<ITaxGet[]> {
    return this.http.get<ITaxGet[]>(this.apiUrl);
  }

  createTax(newTax: Tax): Observable<ResponseCreate> {
    return this.http.post<ResponseCreate>(
      'http://localhost:3000/tax/create/', 
    newTax
    );
  }

  deleteTax(taxID: Tax): Observable<Message> {
    console.log(taxID)
    return this.http.post<Message>(`http://localhost:3000/tax/delete`, taxID);
  }

  editTax(updatedTax: Tax): Observable<ResponseCreate> {
    console.log(updatedTax);
    const url = `${this.apiUrl}/update/${updatedTax.id}`; // Adjust the URL as needed
    return this.http.put<ResponseCreate>(url, updatedTax);
  }

  updateTaxListData() {
    this.loadTaxListData();
  }
}
