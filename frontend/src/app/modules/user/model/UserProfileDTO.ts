export class UserProfileDTO {
  name: string;
  username: string;
  email: string;
  photo: string;
  birthdate: Date | null = null;

  constructor(
    name: string,
    username: string,
    email: string,
    photo: string,
    birthdate: Date | null
  ) {
    this.name = name;
    this.username = username;
    this.email = email;
    this.photo = photo;
    this.birthdate = birthdate;
  }
}
