import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IUser } from '../model/User.model';
import { ResponseCreateUser } from '../interfaces/ResponseCreateUser.type';
import { IUserGet } from '../model/UserGet.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly URL: string = 'http://localhost:3000/user';

  constructor(private http: HttpClient) {}

  getAll(): Observable<IUserGet[]> {
    return this.http.get<IUserGet[]>(`${this.URL}/getAll`);
  }

  create(user: IUser): Observable<ResponseCreateUser> {
    const response: Observable<ResponseCreateUser> =
      this.http.post<ResponseCreateUser>(
        'http://localhost:3000/auth/new',
        user
      );
    return response;
  }

  delete(username: { username: string }) {
    const response: Observable<{ mensaje: string }> = this.http.post<{
      mensaje: string;
    }>(`${this.URL}/disable`, username);

    return response;
  }

  edit(user: IUser): Observable<ResponseCreateUser> {
    const response: Observable<ResponseCreateUser> =
      this.http.post<ResponseCreateUser>(`${this.URL}/edit-user`, user);
    return response;
  }
}
