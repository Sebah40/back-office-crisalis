import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule, FormControl } from '@angular/forms';
import { RouterModule, Routes, Router } from '@angular/router';
import { ClientEntity } from '../model/client-entity';
import { ClientService } from '../service/client.service';
import { RequestBodyCreateOrderDto } from '../model/request-body-create-order-dto';
import { ProductIdAndQuantityDto } from '../model/product-id-and-quantity-dto';
import { SellableGood } from 'src/app/modules/sellable-good/model/sellable-good.model';
import { SellableGoodService } from 'src/app/modules/sellable-good/services/sellable-good.service';
import { OrderService } from 'src/app/order/service/order.service';
import Swal from 'sweetalert2';
import { SweetAlertService } from 'src/app/modules/shared/service/sweet-alert.service';

@Component({
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.css'],
})
export class CreateOrderComponent implements OnInit {
  clientId?: any;
  orderState = new FormControl('');
  id = new FormControl(null);
  title = 'Crear pedido';
  selectedClient?: any;
  showClient = this.selectedClient == null ? 'none' : 'inline';
  searchbar = new FormControl('');
  selectedItems = new FormControl('');
  quant?: any;
  itemList?: SellableGood[];
  productList: ProductIdAndQuantityDto[] = [];
  productQuantity?: ProductIdAndQuantityDto;
  productIdAndQuantity?: ProductIdAndQuantityDto;
  createOrder?: RequestBodyCreateOrderDto;
  newOrder?: RequestBodyCreateOrderDto;

  constructor(
    private orderService: OrderService,
    private clientService: ClientService,
    private sellableGoodService: SellableGoodService,
    private router: Router,
    private sweet: SweetAlertService
  ) {}

  addClient(id: any): void {
    this.clientId = id;
    var element: any = document.getElementById('id-' + this.clientId);
    var bool = element?.classList.contains('selected');
    if (this.selectedClient != null && this.selectedClient != undefined) {
      if (this.selectedClient.id == this.clientId) {
        this.selectedClient = null;
      }
    } else {
      this.selectedClient = this.clientList.find(
        (client) => client.id === this.clientId
      );
    }
    var all = document.querySelectorAll('.selected');
    all.forEach((elem) => {
      elem?.classList.remove('selected');
      elem.textContent = 'Seleccionar';
    });
    if (!bool) {
      element?.classList.add('selected');
      element.textContent = 'Desasignar';
    }
    this.disabled();
  }

  loadItemList(): void {
    this.sellableGoodService.getAll().subscribe((itemList) => {
      this.itemList = itemList;
      console.log(this.itemList);
    });
  }

  disabled() {
    var element: any = document.getElementById('submit');
    if (this.selectedClient == null || this.productList.length == 0) {
      element.setAttribute('disabled', '');
    } else {
      element.removeAttribute('disabled');
    }
  }

  select(id: any): void {
    this.quant = document.getElementById(`${id}`);
    var btn: any = document.getElementById(`btn-${id}`);
    const warranty: any = document.getElementById(`warranty-${id}`);
    console.log(warranty);

    if (this.productList.find((prod) => prod.productId === id)) {
      this.productList = this.productList.filter(
        (prod) => prod.productId !== id
      );
    }
    if (
      this.quant.value > 0 &&
      this.quant.value != null &&
      this.quant.value != undefined
    ) {
      this.productQuantity = new ProductIdAndQuantityDto(
        id,
        Number(this.quant.value)
      );
      this.productQuantity.warrantyYear = warranty ? warranty.value : 0;
      this.productList.push(this.productQuantity);
      btn?.setAttribute('disabled', '');
      btn.textContent = 'Agregado';
      btn.classList.remove('btn-primary');
      btn.classList.add('btn-secondary');
    }
    this.disabled();
  }

  undo(id: any): void {
    var btn: any = document.getElementById(`btn-${id}`);
    btn?.removeAttribute('disabled');
    btn.textContent = 'Agregar';
    btn.classList.remove('btn-secondary');
    btn.classList.add('btn-primary');
    this.productList = this.productList.filter((prod) => prod.productId !== id);
    this.quant = document.getElementById(`${id}`);
    this.quant.value = 0;
    this.disabled();
  }

  loadClients(): any[] {
    this.clientService.getClients().subscribe(
      (clientList) => {
        this.clientList = clientList;
        console.log(this.clientList);
      },
      (err) => {
        console.log(err);
      }
    );
    return this.clientList;
  }

  ngOnInit(): void {
    this.loadClients();
    this.loadItemList();
    this.disabled();
  }

  clientList: ClientEntity[] = [];
  searchbarFilter(query: string): void {
    this.clientService.searchbar(query).subscribe(
      (clientList) => {
        this.clientList = clientList;
        console.log(this.clientList);
      },
      (err) => {
        console.log(err);
      }
    );
  }
  redirect(id: any): void {
    this.router.navigate(['/' + id]);
  }
  submitOrder(): void {
    this.newOrder = new RequestBodyCreateOrderDto(
      this.clientId,
      this.productList
    );
    var ord: any;
    this.orderService.save(this.newOrder).subscribe((res) => {
      ord = res;
      res;
    });

    this.sweet.showAlert('Pedido creado con éxito', 'success');
    setTimeout(() => {
      this.redirect('order/getAll');
    }, 1500);
  }

  onChange(id: any) {
    const input = document.getElementById(id) as any;
    console.log(input);
    if (input?.value < 0) {
      input.value = 0;
    }
  }
}
