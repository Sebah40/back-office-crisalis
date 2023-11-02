import { Component, OnInit } from '@angular/core';
import { IEnterpriseGet } from './model/enterpriseGet.model';
import { EnterpriseService } from './service/client-list.service';

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {
  public enterpriseListData: IEnterpriseGet[] = [];
  public editableValue: boolean = true;

  constructor(private enterpriseService: EnterpriseService) {}

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
        alert(response.mensaje);
      },
      error: (error: any) => {
        console.log(error);
      },
    });
  }
}