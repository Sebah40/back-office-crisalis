import {
  Component,
  OnInit,
  OnChanges,
  AfterContentInit,
  DoCheck,
} from '@angular/core';
import { OrderDTO } from '../model/order-dto';
import { OrderService } from '../service/order.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SellableGood } from 'src/app/modules/sellable-good/model/sellable-good.model';
import { ProductIdAndQuantityDto } from '../model/product-id-and-quantity-dto';
import { ClientService } from '../service/client.service';
import { SellableGoodService } from 'src/app/modules/sellable-good/services/sellable-good.service';
import { RequestBodyCreateOrderDto } from '../model/request-body-create-order-dto';
import Swal from 'sweetalert2';
import { ClientEntity } from '../model/client-entity';
import { OrderDetailDTO } from '../model/order-details-dto';
import { SweetAlertService } from 'src/app/modules/shared/service/sweet-alert.service';

@Component({
  selector: 'app-edit-order',
  templateUrl: './edit-order.component.html',
  styleUrls: ['./edit-order.component.css'],
})
export class EditOrderComponent implements OnInit, OnChanges {
  clientId?: any;
  order?: any;
  itemList?: SellableGood[];
  productList: ProductIdAndQuantityDto[] = [];
  productQuantity?: ProductIdAndQuantityDto;
  productIdAndQuantity?: ProductIdAndQuantityDto;
  id?: any;
  quant?: any;
  newOrder?: RequestBodyCreateOrderDto;
  client?: ClientEntity;
  detailList: OrderDetailDTO[] = [];
  disable: boolean = true;

  constructor(
    private orderService: OrderService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private clientService: ClientService,
    private sellableGoodService: SellableGoodService,
    private sweet: SweetAlertService
  ) {}

  disabled() {
    var element: any = document.getElementById('submit');

    if (this.detailList.length == 0) {
      element.setAttribute('disabled', '');
      this.disable = true;
    } else {
      element.removeAttribute('disabled');
      this.disable = false;
    }
    if (this.order.orderState === 'CANCELED') {
      element.setAttribute('disabled', '');
    }
  }

  loadItemList(): void {
    this.sellableGoodService.getAll().subscribe((itemList) => {
      this.itemList = itemList;
    });
  }
  getOrder(): any {
    this.orderService.getOrder(this.id).subscribe((res) => {
      this.order = new OrderDTO(
        this.id,
        res.client,
        res.orderState,
        res.dateCreated,
        res.orderDetailDTOList
      );
      this.clientId = res.client.id;
      return this.order;
    });
  }
  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params) => {
      this.id = params['id'];
    });
    this.getOrder();
    this.loadItemList();
    this.disabled();
  }
  ngOnChanges(): void {
    this.disabled();
  }

  undo(id: any, bool: boolean): void {
    var btn: any = document.getElementById(bool ? `btn-${id}` : `det-${id}`);
    btn?.removeAttribute('disabled');
    btn.textContent = 'Agregar';
    btn.classList.remove('btn-secondary');
    btn.classList.add('btn-primary');
    if (bool) {
      this.productList = this.productList.filter(
        (prod) => prod.productId !== id
      );
    }
    this.detailList = this.detailList.filter((det) => det.id !== id);
    this.quant = document.getElementById(`${id}`);
    this.quant.value = 0;
    this.disabled();
  }

  remove(detail: OrderDetailDTO) {
    this.order?.orderDetailDTOList.map((det: OrderDetailDTO) => {
      if (det.id == detail.id) {
        det.quantity = 0;
      }
    });
    this.detailList = [];
    this.submitOrder();
  }

  select(item: SellableGood, id: any): void {
    this.quant = document.getElementById(
      id == null ? `order-${item.id}` : `det-val-${id}`
    );
    var btn: any = document.getElementById(
      id == null ? `btn-${item.id}` : `det-${id}`
    );
    if (this.productList.find((prod) => prod.productId === item.id)) {
      this.productList = this.productList.filter(
        (prod) => prod.productId !== item.id
      );
    }
    if (
      this.quant.value > 0 &&
      this.quant.value != null &&
      this.quant.value != undefined
    ) {
      this.detailList.push(
        new OrderDetailDTO(id, item?.price, this.quant.value, item)
      );
      btn?.setAttribute('disabled', '');
      btn.textContent = 'Agregado';
      btn.classList.remove('btn-primary');
      btn.classList.add('btn-secondary');
    }
    this.disable = false;
    if (id != null) {
      this.disabled();
    }
  }

  submitOrder(): void {
    this.order.orderDetailDTOList.push(...this.detailList);
    try {
      this.orderService.update(this.order).subscribe((res) => {
        res;
      });
      this.sweet.showAlert('Operación exitosa', 'success');
      setTimeout(() => {
        this.router.navigate(['/order/getAll']);
      }, 1500);
      setTimeout(() => {
        this.getOrder();
      }, 2500);
    } catch (err) {
      this.sweet.showAlert('Ha ocurrido un error', 'error');
    }
    this.detailList = [];
    this.disabled();
  }
}
