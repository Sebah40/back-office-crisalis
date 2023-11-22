import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { SellableGood } from 'src/app/modules/sellable-good/model/sellable-good.model';
import { ClientServiceService } from '../../service/client-service.service';
import Swal from 'sweetalert2';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-client-services-list',
  templateUrl: './client-services-list.component.html',
  styleUrls: ['./client-services-list.component.css']
})
export class ClientServicesListComponent implements OnInit {
  title = "Servicios activos"
  services: SellableGood[] = [];

  constructor(private route: ActivatedRoute,
    private clientService: ClientServiceService,
    private location: Location) {}

  ngOnInit(): void {
    this.getClientServices();
  }

  getClientServices() {
    this.clientService.getClientServices(this.getClientId()).subscribe(services => this.services = services);
  }

  getClientId():number {
    return Number(this.route.snapshot.paramMap.get('id'));
  }

  goBack():void {
    this.location.back();
  }

  toData(service: SellableGood):(string|undefined)[] {
    return [service.id?.toString(), service.name, service.description, service.price?.toString()];
  }

  removeActiveService(service:SellableGood) {
    this.services = this.services.filter(s => s.id !== service.id);
    this.clientService.removeActiveService(this.getClientId(),service.id ?? -1).subscribe(
      {
        next: (response) => {
          Swal.fire(response, undefined, 'success');
          if(this.services.length <= 0) {
            this.goBack();
          }
        },
        error: (error) => {
          Swal.fire(error, undefined, 'error');
          this.services = [...this.services, service]
        },
      }
    )
  }

}
