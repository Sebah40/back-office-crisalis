import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IPerson } from '../../model/person.model';
import { PersonService } from '../../service/person-list.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ResponseCreate } from 'src/app/components/interfaces/ResponseCreate.type';
import { SweetAlertService } from 'src/app/modules/shared/service/sweet-alert.service';

@Component({
  selector: 'app-person-form',
  templateUrl: './person-form.component.html',
  styleUrls: ['./person-form.component.css'],
})
export class PersonFormComponent implements OnInit {
  public formPerson!: FormGroup;
  public editOrCreateText: string = '';
  public isEditing: boolean = false;

  public personEdit: IPerson = {
    id: null,
    firstName: '',
    lastName: '',
    dni: '',
    active: true,
    beneficiary: false,
  };

  constructor(
    private formBuilder: FormBuilder,
    private personService: PersonService,
    private route: ActivatedRoute,
    private router: Router,
    private sweet: SweetAlertService
  ) {}

  ngOnInit(): void {
    const state = history.state;
    this.route.params.subscribe((params) => {
      const id = params['id'];
      if (id) {
        this.personEdit.id = id;
        this.personEdit.dni = state.dni;
        this.personEdit.firstName = state.firstName;
        this.personEdit.lastName = state.lastName;
        this.personEdit.active = state.active;
        this.personEdit.beneficiary = state.beneficiary;
      }
    });

    this.isEditing = this.personEdit.dni.length ? true : false;

    this.formPerson = this.formBuilder.group({
      id: [this.personEdit.id],
      dni: [
        this.personEdit.dni,
        [Validators.required, Validators.minLength(7)],
      ],
      firstName: [
        this.personEdit.firstName,
        [Validators.required, Validators.minLength(3)],
      ],
      lastName: [
        this.personEdit.lastName,
        [Validators.required, Validators.minLength(2)],
      ],
    });

    this.setButtonText();
  }

  onSubmit() {
    const newPerson: IPerson = {
      id: this.formPerson.value.id,
      dni: this.formPerson.value.dni,
      firstName: this.formPerson.value.firstName,
      lastName: this.formPerson.value.lastName,
      active: true,
      beneficiary: false,
    };
    if (this.isEditing) {
      this.editPerson(newPerson).subscribe({
        next: (response) => {
          if ('mensaje' in response) {
            this.sweet.showAlert(response.mensaje, 'success');
          } else {
            throw response;
          }
        },
        error: (error: HttpErrorResponse) => {
          this.sweet.showAlert(error.error.mensaje, 'error');
        },
      });
    } else {
      this.createPerson(newPerson).subscribe({
        next: (response) => {
          if ('mensaje' in response) {
            this.sweet.showAlert(response.mensaje, 'success');
          } else {
            throw response;
          }
        },
        error: (error: HttpErrorResponse) => {
          console.log(error.error.mensaje);
          this.sweet.showAlert(error.error.mensaje, 'error');
        },
      });
    }
  }

  setButtonText() {
    if (this.personEdit.id) {
      this.editOrCreateText = 'Editar Persona';
    } else {
      this.editOrCreateText = 'Crear Persona';
    }
  }

  createPerson(person: IPerson): Observable<ResponseCreate> {
    return this.personService.create(person);
  }

  editPerson(person: IPerson): Observable<ResponseCreate> {
    return this.personService.edit(person);
  }

  goToPersonList() {
    this.router.navigate(['/person']);
  }
}
