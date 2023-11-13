import { SellableGood } from "../../sellable-good/model/sellable-good.model";

export interface EnterpriseClient {
  id: number;
  beneficiary: boolean;
  activeServices: SellableGood[];
  businessName: string;
  cuit: string;
  date: string;
  firstNameResponsible: string;
  lastNameResponsible: string;
  dniResponsible: string;
  active: boolean;
}
