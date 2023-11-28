import { inject } from '@angular/core';
import { Router } from '@angular/router';

type Permission = {
  authority: string;
};

export const userGuard = (): boolean => {
  const router = inject(Router);
  const data = sessionStorage.getItem('AuthAuthorities');
  if (data !== null) {
    const permissionsObj: Permission[] = JSON.parse(data);
    const permissions = permissionsObj.map((e: any) => e.authority);
    if (permissions.includes('ROLE_USER')) {
      return true;
    }
  }
  router.navigate(['/login']);
  return false;
};
