import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { IUser } from '../model/User.model';
import { ResponseCreate } from 'src/app/components/interfaces/ResponseCreate.type';
import { IUserGet } from '../model/UserGet.model';
import { UserProfileDTO } from '../model/UserProfileDTO';
import { EditProfileDTO } from '../model/EditProfileDTO';
import { TEditPassword } from '../model/TEditPassword';

type User = { username: string };
type Message = { mensaje: string };

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly URL: string = 'http://localhost:3000/user';

  private userListData: BehaviorSubject<IUserGet[]> = new BehaviorSubject<
    IUserGet[]
  >([]);
  userListData$ = this.userListData.asObservable();

  constructor(private http: HttpClient) {
    this.loadUserListData(); // Cargar la lista de usuarios al iniciar el servicio
  }

  // Cargar la lista de usuarios
  private loadUserListData() {
    this.getAll().subscribe((users) => {
      this.userListData.next(users);
    });
  }

  getAll(): Observable<IUserGet[]> {
    return this.http.get<IUserGet[]>(`${this.URL}/getAll`);
  }

  create(user: IUser): Observable<ResponseCreate> {
    return this.http.post<ResponseCreate>(
      'http://localhost:3000/auth/new',
      user
    );
  }

  delete(username: User): Observable<Message> {
    console.log(username);
    return this.http.post<Message>(`${this.URL}/disable`, username);
  }

  edit(user: IUser): Observable<ResponseCreate> {
    return this.http.post<ResponseCreate>(`${this.URL}/edit-user`, user);
  }

  getProfile(): Observable<UserProfileDTO> {
    return this.http.get<UserProfileDTO>(`${this.URL}/profile`);
  }
  editProfile(userEdited: EditProfileDTO): Observable<ResponseCreate> {
    return this.http.put<ResponseCreate>(
      `${this.URL}/edit-profile`,
      userEdited
    );
  }

  editPassword(passEdited: TEditPassword) {
    return this.http.put<ResponseCreate>(
      `${this.URL}/edit-password`,
      passEdited
    );
  }
  uploadPhoto(formData: FormData) {
    return this.http.post<ResponseCreate>(`${this.URL}/upload-photo`, formData);
  }

  // Actualizar la lista de usuarios
  updateUserListData() {
    this.loadUserListData();
  }
}
