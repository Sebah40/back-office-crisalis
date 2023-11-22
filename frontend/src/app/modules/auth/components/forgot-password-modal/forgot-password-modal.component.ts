import { Component, ElementRef } from '@angular/core';
import { AuthService } from '../../service/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-forgot-password-modal',
  templateUrl: './forgot-password-modal.component.html',
  styleUrls: ['./forgot-password-modal.component.css'],
})
export class ForgotPasswordModalComponent {
  constructor(private authService: AuthService, private el: ElementRef) {}
  email!: string;
  sendEmail() {
    this.authService.recoverPassword(this.email).subscribe({
      next: (response) => {
        if ('mensaje' in response) {
          Swal.fire(response.mensaje, undefined, 'success');
          this.closeModal();
        } else {
          throw response;
        }
      },
      error: (error) => {
        console.log(error.error.mensaje);
        Swal.fire(error.error.mensaje, undefined, 'error');
      },
    });
  }
  closeModal() {
    const modalElement = this.el.nativeElement.querySelector('.modal');
    console.log(modalElement);
    if (modalElement) {
      modalElement.classList.remove('show');
      document.body.classList.remove('modal-open');

      const modalBackdropElement = document.querySelector('.modal-backdrop');
      if (modalBackdropElement) {
        modalBackdropElement.remove();
      }

      // Elimina la propiedad 'style' que oculta el modal
      modalElement.style.display = 'none';
    }
  }
}
