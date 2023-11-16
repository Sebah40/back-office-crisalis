export class ClientEntity {
  id?: number;
  beneficiary?: boolean;
  businessName: string;
  dtype: string;
  cuit: string;
  date: Date;
  dniResponsible: string;
  firstNameResponsible: string;
  isActive: boolean; 
  lastNameResponsible: string;
  dni: string;
  firstName: string; 
  lastName: string;


  constructor(id: number, beneficiary: boolean, businessName: string, dtype: string,
    cuit: string, date: Date, dniResponsible: string, firstNameResponsible: string,
    isActive: boolean, lastNameResponsible: string, dni: string, firstName: string, lastName: string) {
    this.id = id;
    this.beneficiary = beneficiary;
    this.businessName = businessName;
    this.cuit = cuit;
    this.dtype = dtype;
    this.date = date;
    this.dni = dni;
    this.dniResponsible = dniResponsible;
    this.firstNameResponsible = firstNameResponsible;
    this.isActive = isActive;
    this.lastNameResponsible = lastNameResponsible;
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
