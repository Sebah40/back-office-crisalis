import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IUser } from '../model/User.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly URL: string = 'http://localhost:8080/user';

  constructor(private http: HttpClient) {}

  getAll(): Observable<IUser[]> {
    return this.http.get<IUser[]>(`${this.URL}getAll`);
  }

  create(user: IUser): Observable<string> {
    const message: Observable<string> = this.http.post<string>(
      'http://localhost:8080/auth/new',
      user
    );
    return message;
  }
}
