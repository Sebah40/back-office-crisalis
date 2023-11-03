import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { Tax } from '../model/tax';
import { ITaxGet } from '../model/tax-get';
import { ResponseCreateUser } from '../../user/interfaces/ResponseCreateUser.type';
type Message = { mensaje: string };

@Injectable({
  providedIn: 'root',
})
export class TaxService {
  private apiUrl = 'http://localhost:3000/tax'; // Replace with your API URL

  constructor(private http: HttpClient) {}

  // Get all taxes
  getAll(): Observable<ITaxGet[]> {
    return this.http.get<ITaxGet[]>(this.apiUrl);
  }

  // Create a new tax
  createTax(newTax: Tax): Observable<ResponseCreateUser> {
    return this.http.post<ResponseCreateUser>(`${this.apiUrl}/create/`, newTax);
  }

  // Delete a tax
  deleteTax(id: number): Observable<Message> {
    const url = `${this.apiUrl}/delete/${id}`;
    return this.http.post<Message>(`${url}`, id);
  }

  editTax(updatedTax: Tax): Observable<ResponseCreateUser> {
    console.log(updatedTax);
    const url = `${this.apiUrl}/update/${updatedTax.id}`; // Adjust the URL as needed
    return this.http.put<ResponseCreateUser>(url, updatedTax);
  }

  //Update tax
  updateTaxData() {
    window.location.reload();
  }
}
