import { OrderState } from "./order-state";
import { ClientEntity } from "./client-entity";
import { OrderDetailDTO } from "./order-details-dto";
import { SellableGood } from "src/app/modules/sellable-good/model/sellable-good.model";

export class OrderDTO {
  id?: number;
  client: ClientEntity;
  orderState: OrderState;
  dateCreated: Date;
  orderDetailDTOList: OrderDetailDTO[];
  service?:SellableGood;

  constructor(id: number, client: ClientEntity, orderState: OrderState, dateCreated: Date, orderDetailDTOList: OrderDetailDTO[]) {
    this.id = id;
    this.client = client;
    this.orderState = orderState;
    this.dateCreated = dateCreated;
    this.orderDetailDTOList = orderDetailDTOList;
  }
}
