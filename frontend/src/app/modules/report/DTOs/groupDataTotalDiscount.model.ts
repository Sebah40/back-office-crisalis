export interface GroupClientForDiscount {
  client: string;
  services: GroupServices[];
}
export interface GroupServices {
  service: string;
  totalDiscount: number;
  items: OrderDataDiscount[];
}

export interface OrderDataDiscount {
  orderNum: number;
  discount: number;
  date: Date;
}
