import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { UserProfileDTO } from '../../model/UserProfileDTO';
import { EditProfileDTO } from '../../model/EditProfileDTO';
import Swal from 'sweetalert2';
import { HttpErrorResponse } from '@angular/common/http';
import { TEditPassword } from '../../model/TEditPassword';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  user!: UserProfileDTO;
  userEdited!: EditProfileDTO;
  isEditing: boolean = false;
  isEditingImg: boolean = false;
  showChangePasswordFields = false;
  oldPassword: string = '';
  newPassword: string = '';
  selectedFile!: File;

  constructor(private userService: UserService) {}
  ngOnInit(): void {
    this.isEditing = false;
    this.get();
  }

  get() {
    this.userService.getProfile().subscribe({
      next: (res: UserProfileDTO) => {
        this.user = new UserProfileDTO(
          res.name,
          res.username,
          res.email,
          res.photo,
          res.birthdate
        );

        this.userEdited = new EditProfileDTO(
          this.user.name,
          this.user.email,
          this.user.birthdate
        );
      },
      error: (error) => {
        console.error('Error al obtener el perfil:', error);
      },
    });
  }
  toggleChangePassword() {
    this.showChangePasswordFields = !this.showChangePasswordFields;
  }
  changePassword() {
    const passwordEdited: TEditPassword = {
      oldPassword: this.oldPassword,
      newPassword: this.newPassword,
    };
    this.userService.editPassword(passwordEdited).subscribe({
      next: (response) => {
        if ('mensaje' in response) {
          Swal.fire(response.mensaje, undefined, 'success');
          this.showChangePasswordFields = false;
          this.oldPassword = '';
          this.newPassword = '';
        } else {
          throw response;
        }
      },
      error: (error: HttpErrorResponse) => {
        console.log(error.error.mensaje);
        Swal.fire(error.error.mensaje, undefined, 'error');
        this.showChangePasswordFields = false;
        this.oldPassword = '';
        this.newPassword = '';
      },
    });
  }

  setIsEditing() {
    this.isEditing = !this.isEditing;
  }
  setIsEditingImg() {
    this.isEditingImg = !this.isEditingImg;
  }
  editProfile() {
    this.userService.editProfile(this.userEdited).subscribe({
      next: (response) => {
        if ('mensaje' in response) {
          Swal.fire(response.mensaje, undefined, 'success');
          this.get();
        } else {
          throw response;
        }
      },
      error: (error: HttpErrorResponse) => {
        console.log(error.error.mensaje);
        Swal.fire(error.error.mensaje, undefined, 'error');
      },
    });
    this.isEditing = false;
  }

  async showAlert() {
    const resultado = await Swal.fire({
      title: '¿Estás seguro?',
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Sí, estoy seguro',
      cancelButtonText: 'Cancelar',
    });

    // Maneja la respuesta
    if (resultado.isConfirmed) {
      this.changePassword();
    } else if (resultado.dismiss === Swal.DismissReason.cancel) {
    }
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0] as File;
  }
  uploadPhoto() {
    if (this.selectedFile) {
      const formData = new FormData();
      formData.append('file', this.selectedFile);

      this.userService.uploadPhoto(formData).subscribe({
        next: (response) => {
          if ('mensaje' in response) {
            Swal.fire(response.mensaje, undefined, 'success');
            location.reload();
          } else {
            throw response;
          }
        },
        error: (error: HttpErrorResponse) => {
          console.log(error.error.mensaje);
          Swal.fire(error.error.mensaje, undefined, 'error');
        },
      });
      this.isEditingImg = false;
    }
  }
}
