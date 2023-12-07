import { Component, OnInit } from '@angular/core';
import { IEnterpriseGet } from '../../model/enterpriseGet.model';
import { EnterpriseService } from '../../service/enterprise-list.service';
import { SweetAlertService } from 'src/app/modules/shared/service/sweet-alert.service';

@Component({
  selector: 'app-client-list',
  templateUrl: './enterprise-list.component.html',
  styleUrls: ['./enterprise-list.component.css'],
})
export class ClientListComponent implements OnInit {
  public enterpriseListData: IEnterpriseGet[] = [];
  public editableValue: boolean = true;

  constructor(
    private enterpriseService: EnterpriseService,
    private sweet: SweetAlertService
  ) {}

  ngOnInit(): void {
    this.enterpriseService.enterpriseListData$.subscribe((enterprises) => {
      this.enterpriseListData = enterprises; // Actualiza la lista de empresas
    });

    // Inicialmente, obtén la lista de empresas
    this.enterpriseService.getAll().subscribe((enterprises) => {
      this.enterpriseListData = enterprises;
    });
  }
  deleteEnterprise(entity: any) {
    console.log('enterpriseList', entity);
    const enterprise: { id: number } = { id: entity.id };
    console.log('enterpriseList', enterprise);
    this.enterpriseService.delete(enterprise).subscribe({
      next: (response: any) => {
        this.enterpriseService.updateEnterpriseListData();
        this.sweet.showAlert(response.mensaje, 'success');
      },
      error: (error: any) => {
        console.log(error);
      },
    });
  }
}
