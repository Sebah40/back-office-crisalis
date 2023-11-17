import { OrderState } from "./order-state";
import { ClientEntity } from "./client-entity";
import { OrderDetailDTO } from "./order-details-dto";

export class OrderDTO {
  id?: number;
  client: ClientEntity;
  orderState: OrderState;
  dateCreated: Date;
  orderDetailDTOList: OrderDetailDTO[];

  constructor(id: number, client: ClientEntity, orderState: OrderState, dateCreated: Date, orderDetailDTOList: OrderDetailDTO[]) {
    this.id = id;
    this.client = client;
    this.orderState = orderState;
    this.dateCreated = dateCreated;
    this.orderDetailDTOList = orderDetailDTOList;
  }
}
