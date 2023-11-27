export interface GroupClient {
  client: string;
  items: GroupByGood[];
}

export interface GroupByGood {
  sellablegood: string;
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
}