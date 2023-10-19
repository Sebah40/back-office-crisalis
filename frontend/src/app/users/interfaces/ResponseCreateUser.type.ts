import { HttpErrorResponse } from '@angular/common/http';

export type ResponseCreateUser = { mensaje: string } | HttpErrorResponse;
