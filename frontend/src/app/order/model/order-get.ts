import { OrderState } from "./order-state";
import { ClientEntity } from "./client-entity";
import { OrderDetailDTO } from "./order-details-dto";
import { SellableGood } from "src/app/modules/sellable-good/model/sellable-good.model";

export interface IOrderGet {
    id?: number;
  client: ClientEntity;
  orderState: OrderState;
  dateCreated: Date;
  orderDetailDTOList: OrderDetailDTO[];
  service?:SellableGood;
}