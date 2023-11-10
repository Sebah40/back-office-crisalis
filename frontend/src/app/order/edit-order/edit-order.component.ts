import { Component, OnInit } from '@angular/core';
import { OrderDTO } from '../order-dto';
import { OrderService } from '../service/order.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SellableGood } from 'src/app/modules/sellable-good/model/sellable-good.model';
import { ProductIdAndQuantityDto } from '../product-id-and-quantity-dto';
import { ClientService } from '../service/client.service';
import { SellableGoodService } from 'src/app/modules/sellable-good/services/sellable-good.service';
import { RequestBodyCreateOrderDto } from '../request-body-create-order-dto';
import Swal from 'sweetalert2';
import { ClientEntity } from '../client-entity';
import { OrderDetailDTO } from '../order-details-dto';

@Component({
  selector: 'app-edit-order',
  templateUrl: './edit-order.component.html',
  styleUrls: ['./edit-order.component.css']
})
export class EditOrderComponent implements OnInit {
  clientId?: any;
  order?:any;
  itemList?: SellableGood[];
  productList: ProductIdAndQuantityDto[] = [];
  productQuantity?: ProductIdAndQuantityDto;
  productIdAndQuantity?: ProductIdAndQuantityDto;
  id?:any;
  quant?:any;
  newOrder?:RequestBodyCreateOrderDto;
  client?: ClientEntity
  detailList: OrderDetailDTO[] = [];

  constructor(private orderService: OrderService,
    private activatedRoute: ActivatedRoute, private router: Router,
    private clientService: ClientService, private sellableGoodService: SellableGoodService) {}

  loadOrder(id: any): any {
    this.orderService.getOrder(id).subscribe(res => {
      this.order = res;
      return this.order;
    })
  }

  disabled() {
    var element: any = document.getElementById('submit');
    if(this.order.orderDetailDTOList == 0 ) {
      element.setAttribute('disabled', '')
    } else {
      element.removeAttribute('disabled')
    }
  }


  loadItemList(): void {
    this.sellableGoodService.getAll().subscribe(itemList => {
      this.itemList = itemList;
      console.log(this.itemList);
    })
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params) => {
      this.id = params['id']
    })

    this.orderService.getOrder(this.id).subscribe(res => {
      this.order = new OrderDTO(this.id, res.client, res.orderState, res.dateCreated, res.orderDetailDTOList);
      this.clientId = res.client.id;
      return this.order;
    })
    this.loadItemList();
  }
  undo(id: any, bool: boolean): void {
    var btn: any = document.getElementById(bool ? `btn-${id}` : `det-${id}`);
    btn?.removeAttribute('disabled');
    btn.textContent = '+';
    btn.classList.remove("btn-secondary");
    btn.classList.add("btn-primary");
    this.productList = this.productList.filter(prod => prod.productId !== id)
    this.quant = document.getElementById(`${id}`);
    this.quant.value = 0;
    this.disabled();
  }

  remove(detail: OrderDetailDTO) {
    this.detailList.push(new OrderDetailDTO(detail.id, detail.priceSell, 0 ,detail.sellableGood))
    this.submitOrder();
}

  select(item: SellableGood, id:any): void {
    console.log(item, id);
    this.quant = document.getElementById(id == null ? `order-${item.id}` : `det-val-${id}`);
    var btn: any = document.getElementById(id == null ? `btn-${item.id}` : `det-${id}`);

    if(this.productList.find(prod => prod.productId === item.id)) {
      this.productList = this.productList.filter(prod => prod.productId !== item.id)
    }
    if(this.quant.value > 0 && this.quant.value != null && this.quant.value != undefined) {

    this.detailList.push(new OrderDetailDTO(id, item?.price, this.quant.value ,item))

    btn?.setAttribute('disabled', '')
    btn.textContent = 'Agregado';
    btn.classList.remove("btn-primary");
    btn.classList.add("btn-secondary");
    }
    this.disabled();
  }

  submitOrder(): void {
    this.order.orderDetailDTOList.push(...this.detailList);
    console.log(this.detailList)
    console.log(this.order.orderDetailDTOList)
    var ord: any;
    console.log(this.order)
    try {
      this.orderService.update(this.order).subscribe(res => {
        ord = res;
        res
        console.log(res);
      });
      Swal.fire('Operación exitosa', undefined, 'success')
      setTimeout(() => {
        //this.redirect('order/getAll');
      }, 2500);
      this.loadOrder(this.order.id);
    } catch (err) {
      Swal.fire('Ha ocurrido un error', undefined, 'error')
      setTimeout(() => {
        //this.redirect('order/getAll');
      }, 2500);
  }}

}
