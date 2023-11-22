export class ProductIdAndQuantityDto {
  productId: number;
  quantity: number;
  warrantyYear?: number;

  constructor(productId: number, quantity: number) {
    this.productId = productId;
    this.quantity = quantity;
  }
}
