import { OrderState } from "./order-state";
import { ClientEntity } from "./client-entity";
import { OrderDetail } from "./order-details";

export class OrderDto {
  id?: number;
  client: ClientEntity;
  orderState: OrderState;
  dateCreated: Date;
  orderDetailList: OrderDetail;

  constructor(client: ClientEntity, orderState: OrderState, dateCreated: Date, orderDetailList: OrderDetail) {
    this.client = client;
    this.orderState = orderState;
    this.dateCreated = dateCreated;
    this.orderDetailList = orderDetailList;
  }
}
