import { Component, OnInit, Input } from '@angular/core';
import { TaxService } from '../../service/tax.service';
import { EntityContainerComponent } from '../../../shared/components/entity-container-component/entity-container.component';
import { SweetAlertService } from 'src/app/modules/shared/service/sweet-alert.service';
@Component({
  selector: 'app-tax',
  templateUrl: './tax-list.component.html',
  styleUrls: ['./tax-list.component.css'],
})
export class TaxListComponent<T extends object> implements OnInit {
  taxListData: any[] = [];
  @Input() entities: T[] = [];
  entityKeys: (keyof T)[] = [];
  @Input() title: string = '';
  @Input() isEditable: boolean = false;

  constructor(
    private taxService: TaxService,
    private sweet: SweetAlertService
  ) {}

  ngOnInit() {
    this.taxService.taxListData$.subscribe((data) => {
      this.taxListData = data;
    });

    this.taxService.getAll().subscribe((data) => {
      this.taxListData = data;
    });
  }

  getData(): any[] {
    console.log(
      this.entities.map((item) => this.entityKeys.map((key) => item[key]))
    );
    return this.entities.map((item) => this.entityKeys.map((key) => item[key]));
  }
  deleteTax(entity: any) {
    console.log('taxList', entity);
    const tax: { id: number; taxName: string; taxPercentage: number } = {
      id: entity.id,
      taxName: '',
      taxPercentage: 0,
    };

    console.log('tax', tax);
    this.taxService.deleteTax(tax).subscribe({
      next: (response: any) => {
        this.sweet.showAlert('Se eliminó correctamente', 'success');
        this.taxService.updateTaxListData();
      },
      error: (error: any) => {
        this.sweet.showAlert('Ocurrió un error', 'error');
      },
    });
  }
}
