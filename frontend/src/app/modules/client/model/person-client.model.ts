import { SellableGood } from "../../sellable-good/model/sellable-good.model";

export interface PersonClient {
  id: number;
  beneficiary: boolean;
  activeServices: SellableGood[];
  lastName: string;
  firstName: string;
  dni: string;
  active: boolean;
}
