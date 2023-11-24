export class ClientDTO {
  id: number;
  name: string;
  dniOrCuit: string;
  isSelected: boolean = false;

  constructor(id: number, name: string, dniOrCuit: string) {
    this.id = id;
    this.dniOrCuit = dniOrCuit;
    this.name = name;
  }
}
