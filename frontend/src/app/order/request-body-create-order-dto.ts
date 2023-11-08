import { ProductIdAndQuantityDto } from "./product-id-and-quantity-dto";

export class RequestBodyCreateOrderDto {
  clientId: number;
  productIdList: ProductIdAndQuantityDto[];

  constructor(clientId: number, productIdList: ProductIdAndQuantityDto[]) {
    this.clientId = clientId;
    this.productIdList = productIdList;
  }
}
