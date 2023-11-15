import { Component, OnInit } from '@angular/core';
import { Client } from '../../model/client.model';
import { PersonClient } from '../../model/person-client.model';
import { EnterpriseClient } from '../../model/enterprise-client.model';
import { ClientServiceService } from '../../service/client-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-client2-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientList2Component implements OnInit{
  title = "Lista de suscripciones de clientes";
  clients: Client[] = []

  constructor(private clientService : ClientServiceService, private router: Router){}

  ngOnInit(): void {
      this.getClients();
  }

  getClients(){
    this.clientService.getAll().subscribe(clients => this.clients = clients);
  }



  isPerson(client: Client): client is PersonClient {
    return !('businessName' in client);
  }

  isEnterprise(client: Client): client is EnterpriseClient {
    return 'businessName' in client;
  }

  getType(client: Client) {
    return this.isPerson(client)?"Persona":"Empresa";
  }

  hasActiveServices(client:Client):boolean {
    return client.activeServices.length > 0;
  }

  listActiveServices(client:Client) {
    this.router.navigate(["clients/services",{actualClient: client}]);
  }

  toData(client:Client): string[] {
    if(this.isPerson(client)) {
      return [
        "Persona", 
        client.id.toString(), 
        `${client.lastName} ${client.firstName}`,
        client.dni
      ]
    }else{
      this.isEnterprise(client)
      return [
        "Empresa",
        client.id.toString(),
        client.businessName,
        client.cuit
      ]
    }
  }
}
