import { HttpErrorResponse } from '@angular/common/http';

export type ResponseCreate = { mensaje: string } | HttpErrorResponse;
