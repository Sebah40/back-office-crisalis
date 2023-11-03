import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { IPersonGet } from '../model/personGet.model';
import { HttpClient } from '@angular/common/http';
import { ResponseCreateUser } from 'src/app/modules/user/interfaces/ResponseCreateUser.type';
import { IPerson } from '../model/person.model';

type Person = { id: number };
type Message = { mensaje: string };

@Injectable({
  providedIn: 'root',
})
export class PersonService {
  private readonly URL: string = 'http://localhost:3000/person';

  private personListData: BehaviorSubject<IPersonGet[]> = new BehaviorSubject<
    IPersonGet[]
  >([]);
  personListData$ = this.personListData.asObservable();

  constructor(private http: HttpClient) {
    this.loadPersonListData(); // Cargar la lista de personas al iniciar el servicio
  }

  // Cargar la lista de personas
  private loadPersonListData() {
    this.getAll().subscribe((persons) => {
      this.personListData.next(persons);
    });
  }

  getAll(): Observable<IPersonGet[]> {
    return this.http.get<IPersonGet[]>(`${this.URL}/getAll`);
  }

  create(person: IPerson): Observable<ResponseCreateUser> {
    return this.http.post<ResponseCreateUser>(`${this.URL}/create`, person);
  }

  delete(id: Person): Observable<Message> {
    console.log(id);
    return this.http.post<Message>(`${this.URL}/disable`, id);
  }

  edit(person: IPerson): Observable<ResponseCreateUser> {
    return this.http.post<ResponseCreateUser>(`${this.URL}/edit`, person);
  }

  // Actualizar la lista de personas
  updatePersonListData() {
    this.loadPersonListData();
  }
}
