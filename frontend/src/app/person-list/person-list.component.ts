import { Component } from '@angular/core';
import { IPersonGet } from './model/personGet.model';
import { PersonService } from './service/person-list.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.css']
})
export class PersonListComponent {

  public personListData: IPersonGet[] = [];
  public editableValue: boolean = true;

  constructor(private personService: PersonService) {}

  ngOnInit(): void {
    this.personService.personListData$.subscribe((persons) => {
      this.personListData = persons; // Actualiza la lista de personas
    });

    // Inicialmente, obtén la lista de usuarios
    this.personService.getAll().subscribe((persons) => {
      this.personListData = persons;
    });
  }
  deletePerson(entity: any) {
    console.log('personList', entity);
    const person: { id: number } = { id: entity.id };
    console.log('personList', person);
    this.personService.delete(person).subscribe({
      next: (response: any) => {
        this.personService.updatePersonListData();
        alert(response.mensaje);
      },
      error: (error: any) => {
        console.log(error);
      },
    });
  }
}