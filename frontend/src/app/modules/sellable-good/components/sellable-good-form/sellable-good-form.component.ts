import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SellableGoodService } from '../../services/sellable-good.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SellableGood } from '../../model/sellable-good.model';
import { HttpErrorResponse } from '@angular/common/http';
import { Tax } from '../../model/tax.model';
import { SweetAlertService } from 'src/app/modules/shared/service/sweet-alert.service';

enum SellableGoodType {
  PRODUCT,
  SERVICE,
}

function isEmpty(value: any): boolean {
  return value === undefined || value === '' || value === null;
}

@Component({
  selector: 'app-sellable-good-form',
  templateUrl: './sellable-good-form.component.html',
  styleUrls: ['./sellable-good-form.component.css'],
})
export class SellableGoodFormComponent implements OnInit {
  selectedType = '';
  @ViewChild('selectedTax') selectedTax!: ElementRef;
  public formSellableGood!: FormGroup;
  public sellableGoodInstance: SellableGood = {
    id: undefined,
    name: '',
    description: '',
    price: 0,
    supportCharge: 0,
    type: '',
    taxes: [],
    active: true,
  };
  public taxes: Tax[] = [];
  public isEnabled = this.taxes.length > 0;

  constructor(
    private formBuilder: FormBuilder,
    private sellableGoodService: SellableGoodService,
    private route: ActivatedRoute,
    private router: Router,
    private sweet: SweetAlertService
  ) {}

  ngOnInit(): void {
    if (!isEmpty(history.state.id)) {
      this.setSellableGood(history.state);
    }

    this.sellableGoodService.getAllTaxes().subscribe((data) => {
      this.taxes = data;
      this.taxes = this.taxes.filter((tax) => !this.sellableGoodHasTax(tax));
      this.isEnabled = this.taxes.length > 0;
    });

    this.formSellableGood = this.formBuilder.group({
      name: [this.sellableGoodInstance.name, [Validators.required]],
      description: [this.sellableGoodInstance.description],
      price: [
        this.sellableGoodInstance.price,
        [Validators.required, Validators.pattern(/^\d+(\.\d+)?$/)],
      ],
      type: [this.sellableGoodInstance.type, [Validators.required]],
      taxes: [this.sellableGoodInstance.taxes],
      charge: [
        this.sellableGoodInstance.supportCharge,
        [Validators.required, Validators.pattern(/^\d+(\.\d+)?$/)],
      ],
    });
  }

  sellableGoodHasTax(tax: Tax): boolean | undefined {
    return this.sellableGoodInstance.taxes?.some((t) => t.id === tax.id);
  }

  setSellableGood(data: any) {
    this.sellableGoodInstance.id = data.id;
    this.sellableGoodInstance.name = data.name;
    this.sellableGoodInstance.description = data.description;
    this.sellableGoodInstance.price = data.price;
    this.sellableGoodInstance.type = data.type;
    this.sellableGoodInstance.taxes = data.taxes;
  }

  isEmpty(value: any): boolean {
    return value === undefined || value === '' || value === null;
  }

  onSubmit() {
    this.sellableGoodInstance.name = this.formSellableGood.value.name;
    this.sellableGoodInstance.description =
      this.formSellableGood.value.description;
    this.sellableGoodInstance.price = this.formSellableGood.value.price;
    this.sellableGoodInstance.type = this.formSellableGood.value.type;
    this.sellableGoodInstance.supportCharge =
      this.formSellableGood.value.charge;
    if (isEmpty(this.sellableGoodInstance.id)) {
      this.sellableGoodService.create(this.sellableGoodInstance).subscribe({
        next: (response) => {
          if ('id' in response) {
            this.sweet.showAlert('Se creo correctamente', 'success');
            this.goToSellableGoodList();
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
      this.sellableGoodService.edit(this.sellableGoodInstance).subscribe({
        next: (response) => {
          if ('mensaje' in response) {
            this.sweet.showAlert(response.mensaje, 'success');
            this.goToSellableGoodList();
          } else {
            throw response;
          }
        },
        error: (error: HttpErrorResponse) => {
          this.sweet.showAlert(error.error.mensaje, 'error');
          alert(error.error.mensaje);
        },
      });
    }
  }

  goToSellableGoodList() {
    this.router.navigate(['/good']);
  }

  addTaxToSellableGood() {
    const tax: Tax = this.taxes.filter(
      (t) => t.id === parseInt(this.selectedTax.nativeElement.value)
    )[0];
    this.sellableGoodInstance.taxes?.push(tax);
    this.taxes = this.taxes.filter((t) => t.id !== tax.id);
    this.isEnabled = this.taxes.length > 0;
  }

  removeTax(tax: Tax) {
    this.sellableGoodInstance.taxes = this.sellableGoodInstance.taxes?.filter(
      (t) => t.id !== tax.id
    );
    this.taxes = [...this.taxes, tax];
    this.isEnabled = this.taxes.length > 0;
  }

  isService() {
    return this.selectedType === 'SERVICE';
  }
}
