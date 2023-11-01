import { Component, OnInit, Input } from '@angular/core';
import { TaxService } from '../service/tax.service';
import { EntityContainerComponent } from '../shared/components/entity-container-component/entity-container.component';
@Component({
  selector: 'app-tax',
  templateUrl: './tax-list.component.html',
  styleUrls: ['./tax-list.component.css']
})
export class TaxListComponent<T extends object> implements OnInit {
  taxListData: any[] = [];
  @Input() entities: T[] = [];
  entityKeys: (keyof T)[] = [];
  @Input() title: string = '';
  @Input() isEditable: boolean = false;

  constructor(private taxService: TaxService) {}

  ngOnInit() {
    this.taxService.getAll().subscribe(data => {
      this.taxListData = data;
    });
  }
  ngOnChanges() {
    if (this.entities && this.entities.length > 0) {
      this.entityKeys = Object.keys(this.entities[0]) as (keyof T)[];
    }
    console.log(this.entities);
  }

  getData(): any[] {
    console.log(
      this.entities.map((item) => this.entityKeys.map((key) => item[key]))
    );
    return this.entities.map((item) => this.entityKeys.map((key) => item[key]));
  }
  deleteTax(entity: any) {
    console.log('taxList', entity);
      const tax: { id: number } = { id: entity.id };
      console.log('tax', tax);
      this.taxService.deleteTax(tax.id).subscribe({
        next: (response: any) => {
          this.taxService.updateTaxData();
        },
        error: (error: any) => {
          console.log(error);
        },
      });
    }

  }