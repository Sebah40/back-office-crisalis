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
      padding: '3.5em',
      background: '#22334B',
      color: '#fAf0e5',
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
