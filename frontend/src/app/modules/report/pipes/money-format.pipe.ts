import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'moneyFormat'
})
export class MoneyFormatPipe implements PipeTransform {

  transform(value: number | null | undefined): string {
    if (value == null) {
      return '';
    }
    return value.toLocaleString('es-AR', { style: 'currency', currency: 'ARS' });
  }
}
