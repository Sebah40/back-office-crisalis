import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { SellableGood } from 'src/app/modules/sellable-good/model/sellable-good.model';
import { ClientServiceService } from '../../service/client-service.service';

@Component({
  selector: 'app-client-services-list',
  templateUrl: './client-services-list.component.html',
  styleUrls: ['./client-services-list.component.css']
})
export class ClientServicesListComponent implements OnInit {

  clientServices: SellableGood[] = [];

  constructor(private route: ActivatedRoute,
    private clientService: ClientServiceService,
    private location: Location) {}

  ngOnInit(): void {
    this.getClientServices();
  }

  getClientServices() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.clientService.getClientServices(id).subscribe(services => this.clientServices = services);
  }

  goBack():void {
    this.location.back();
  }

  toData(service: SellableGood):(string|undefined)[] {
    return [service.id?.toString(), service.name, service.description, service.price?.toString()];
  }

}
