import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NewUser } from '../model/new-user';
import { JwtDto } from '../model/jwt-dto';
import { Login } from '../model/login';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  authURL = 'http://localhost:8080/auth/';
  constructor(private httpClient: HttpClient) { }
  
  public nuevo(newUser: NewUser): Observable<any> {
    return this.httpClient.post<any>(this.authURL + 'nuevo', newUser);
  }

  public login(login: Login): Observable<JwtDto> {
    return this.httpClient.post<JwtDto>(this.authURL + 'login', login);
  }
}