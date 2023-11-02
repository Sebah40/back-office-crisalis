export interface IEnterprise {
    id: number | null,
    businessName: string;
    cuit: string;
    active: boolean;
    beneficiary: boolean,
    firstNameResponsible: string;
    lastNameResponsible: string;
    dniResponsible: string;
    date: Date;
  }