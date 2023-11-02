import { Pipe, PipeTransform } from '@angular/core';
import { Tax } from '../model/tax.model';

@Pipe({
  name: 'formatTaxes'
})
export class FormatTaxesPipe implements PipeTransform {

  transform(taxes?: Array<Tax>): string {
    if (!taxes || !taxes.length) {
      return 'Sin impuestos';
    }
    const formattedTaxes = (taxes||[]).map((tax) => {
      return `${tax.taxName}(${tax.taxPercentage}%)`;
    }).join(', ');
    return formattedTaxes;
  }

}
