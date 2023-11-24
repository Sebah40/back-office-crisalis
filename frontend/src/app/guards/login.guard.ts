import { inject } from '@angular/core';
import { Router } from '@angular/router';

export const loginGuard = (): boolean => {
  const router = inject(Router);
  const data = sessionStorage.getItem('AuthAuthorities');
  if (data !== null) {
    router.navigate(['/home']);
    return false;
  }
  return true;
};
