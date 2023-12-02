import { Injectable } from '@angular/core';
import Swal, { SweetAlertIcon } from 'sweetalert2';

@Injectable({
  providedIn: 'root',
})
export class SweetAlertService {
  constructor() {}

  showAlert(message: string, type: SweetAlertIcon, callback?: any) {
    Swal.fire({
      title: message,
      icon: type,
      timer: 1500,
      showConfirmButton: false,
      timerProgressBar: true,
      backdrop: `
         rgba(0,0,123,0.4)
      `,
    }).then(() => {
      if (typeof callback === 'function') {
        callback();
      }
    });
  }
}
