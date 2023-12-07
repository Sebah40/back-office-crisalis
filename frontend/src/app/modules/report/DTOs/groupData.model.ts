export interface GroupClient {
  client: string;
  items: GroupByGood[];
}

export interface GroupByGood {
  sellablegood: string;
  quantityAccumulator: number;
  subtotalAccumulator: number;
  totalTaxesAccumulator: number;
  discountAccumulator: number;
  totalAccumulator: number;
  items: OrderData[];
}

export interface OrderData {
  orderId: number;
  status: string;
  date: Date;
  quantity: number;
  price: number;
  subtotal: number;
  totalTaxes: number;
  total: number;
  warrantyValue: number;
  supportCharge: number;
  discount: number;
}