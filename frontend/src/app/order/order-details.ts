export class OrderDetail {
  id:number;
  priceSell:number;
  quantity:number;

  constructor(id:number, priceSell:number, quantity:number){
    this.id = id;
    this.priceSell = priceSell;
    this.quantity = quantity;
  }
}