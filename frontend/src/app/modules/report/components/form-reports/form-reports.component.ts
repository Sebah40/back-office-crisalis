import { Component } from '@angular/core';
import { ClientService } from '../../../../order/service/client.service';
import { ClientDTO } from '../../DTOs/clientDTO';

@Component({
  selector: 'app-form-reports',
  templateUrl: './form-reports.component.html',
  styleUrls: ['./form-reports.component.css'],
})
export class FormReportsComponent {
  clientList!: any[];
  selectedClient!: ClientDTO;
  inputValue!: string;
  constructor(private clientService: ClientService) {}
  ngOnInit(): void {
    this.getClientList();
  }
  getClientList() {
    this.clientService.getClients().subscribe({
      next: (res) => {
        this.clientList = res.map((element: any) => {
          return new ClientDTO(
            element.id,
            element.firstName !== undefined
              ? element.firstName + ' ' + element.lastName
              : element.businessName,
            element.dni !== undefined ? element.dni : element.cuit
          );
        });
      },
    });
  }

  searchClient(event: string) {
    if (event.length === 0) {
      this.getClientList();
    }
    this.clientList = this.clientList.filter((element: ClientDTO) => {
      if (element.name.toLowerCase().includes(event.toLowerCase()))
        return element;
      else if (element.dniOrCuit.includes(event)) return element;
      else return null;
    });
  }

  onCheckboxChange(selectedClient: any): void {
    // Deseleccionar los demás clientes
    this.clientList.forEach((client) => {
      if (client !== selectedClient) {
        client.isSelected = false;
      }
    });
  }
  setSelectedClient() {
    this.selectedClient = this.clientList.filter(
      (cliente) => cliente.isSelected === true
    )[0];

    console.log(this.selectedClient);
  }
}
