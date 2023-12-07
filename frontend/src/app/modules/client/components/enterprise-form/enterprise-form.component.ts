import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { IEnterprise } from '../../model/enterprise.model';
import { EnterpriseService } from '../../service/enterprise-list.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ResponseCreate } from 'src/app/components/interfaces/ResponseCreate.type';
import { SweetAlertService } from 'src/app/modules/shared/service/sweet-alert.service';

@Component({
  selector: 'app-enterprise-form',
  templateUrl: './enterprise-form.component.html',
  styleUrls: ['./enterprise-form.component.css'],
})
export class EnterpriseFormComponent implements OnInit {
  public formEnterprise!: FormGroup;
  public editOrCreateText: string = '';
  public isEditing: boolean = false;

  public enterpriseEdit: IEnterprise = {
    id: null,
    businessName: '',
    cuit: '',
    firstNameResponsible: '',
    lastNameResponsible: '',
    dniResponsible: '',
    date: new Date(),
    active: true,
    beneficiary: false,
  };

  constructor(
    private formBuilder: FormBuilder,
    private enterpriseService: EnterpriseService,
    private route: ActivatedRoute,
    private router: Router,
    private sweet: SweetAlertService
  ) {}

  ngOnInit(): void {
    const state = history.state;
    this.route.params.subscribe((params) => {
      const id = params['id'];
      if (id) {
        this.enterpriseEdit.id = id;
        this.enterpriseEdit.businessName = state.businessName;
        this.enterpriseEdit.cuit = state.cuit;
        this.enterpriseEdit.firstNameResponsible = state.firstNameResponsible;
        this.enterpriseEdit.lastNameResponsible = state.lastNameResponsible;
        this.enterpriseEdit.dniResponsible = state.dniResponsible;
        this.enterpriseEdit.date = state.date;
        this.enterpriseEdit.beneficiary = state.beneficiary;
        this.enterpriseEdit.active = state.active;
      }
    });

    this.isEditing = this.enterpriseEdit.businessName.length ? true : false;

    this.formEnterprise = this.formBuilder.group({
      id: [this.enterpriseEdit.id],
      businessName: [
        this.enterpriseEdit.businessName,
        [Validators.required, Validators.minLength(2)],
      ],
      cuit: [
        this.enterpriseEdit.cuit,
        [Validators.required, Validators.minLength(8)],
      ],
      firstNameResponsible: [
        this.enterpriseEdit.firstNameResponsible,
        [Validators.required],
      ],
      lastNameResponsible: [
        this.enterpriseEdit.lastNameResponsible,
        [Validators.required],
      ],
      dniResponsible: [
        this.enterpriseEdit.dniResponsible,
        [Validators.required, Validators.minLength(6)],
      ],
      date: [this.enterpriseEdit.date, [Validators.required]],
    });

    this.setButtonText();
  }

  onSubmit() {
    const newEnterprise: IEnterprise = {
      id: this.formEnterprise.value.id,
      businessName: this.formEnterprise.value.businessName,
      cuit: this.formEnterprise.value.cuit,
      firstNameResponsible: this.formEnterprise.value.firstNameResponsible,
      lastNameResponsible: this.formEnterprise.value.lastNameResponsible,
      dniResponsible: this.formEnterprise.value.dniResponsible,
      active: true,
      beneficiary: false,
      date: this.formEnterprise.value.date,
    };
    if (this.isEditing) {
      this.editEnterprise(newEnterprise).subscribe({
        next: (response) => {
          if ('mensaje' in response) {
            console.log(response.mensaje);
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
    } else {
      this.createEnterprise(newEnterprise).subscribe({
        next: (response) => {
          if ('mensaje' in response) {
            console.log(response.mensaje);
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
    if (this.enterpriseEdit.id) {
      this.editOrCreateText = 'Editar empresa';
    } else {
      this.editOrCreateText = 'Crear empresa';
    }
  }

  createEnterprise(enterprise: IEnterprise): Observable<ResponseCreate> {
    return this.enterpriseService.create(enterprise);
  }

  editEnterprise(enterprise: IEnterprise): Observable<ResponseCreate> {
    return this.enterpriseService.edit(enterprise);
  }

  goToEnterpriseList() {
    this.router.navigate(['/enterprise']);
  }
}
