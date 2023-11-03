import { Tax } from "./tax.model";

export interface SellableGood {
    id?: number;
    name?: string;
    description?: string;
    price?: number;
    supportCharge?: null | number;
    type?: string;
    taxes?: Array<Tax>;
    active?: boolean;
}
