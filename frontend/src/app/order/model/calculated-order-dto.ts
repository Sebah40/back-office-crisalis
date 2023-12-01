import { Client } from "src/app/modules/client/model/client.model";
import { SellableGood } from "src/app/modules/sellable-good/model/sellable-good.model";

export interface CalculatedOrder {
  id: number;
  dateCreated: Date;
  orderState: string;
  client: Client;
  orderDetailList: OrderDetail[];
  discount: number;
  subTotalWithoutDiscount: number;
  total: number;
}

export interface OrderDetail {
  id: number;
  priceSell: number;
  quantity: number;
  sellableGood: SellableGood;
  discount: number;
  supportCharge: number;
  warrantyValue: number;
  subTotalWithoutDiscount: number;
  subTotal: number;
}
