export class ClientEntity {
  id?: number;
  beneficiary?: boolean;
  businessName: string;

  constructor(id: number, beneficiary: boolean, businessName: string) {
    this.id = id;
    this.beneficiary = beneficiary;
    this.businessName = businessName;
  }
}
