export interface OrderByClient {
  clientID: number;
  clientName: string;
  sellableGood: string;
  orderID: number;
  orderStatus: string;
  orderDate: string;
  quantity: number;
  price: number;
  subtotal: number;
  taxes: number;
  total: number;
  discount: number;
  warrantyValue: number;
  supportCharge: number;
}