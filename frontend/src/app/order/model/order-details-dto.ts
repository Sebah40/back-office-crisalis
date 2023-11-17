import { SellableGood } from "../../modules/sellable-good/model/sellable-good.model";

export class OrderDetailDTO {
  id:any;
  priceSell:any;
  quantity:number;
  sellableGood: SellableGood;
  discount?: number;

  constructor(id:any, priceSell:any, quantity:number, sellableGood:SellableGood){
    this.id = id;
    this.priceSell = priceSell;
    this.quantity = quantity;
    this.sellableGood = sellableGood;
  }
}
