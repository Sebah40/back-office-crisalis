export class EditProfileDTO {
  name: string;
  email: string;
  birthdate: Date | null;

  constructor(name: string, email: string, birthdate: Date | null) {
    this.name = name;
    this.email = email;
    this.birthdate = birthdate;
  }
}
