import { Pipe, PipeTransform } from '@angular/core';
import { Tax } from '../model/tax.model';

@Pipe({
  name: 'taxFormatter'
})
export class TaxFormatterPipe implements PipeTransform {
  transform(tax: Tax): string {
    return `${tax.taxName}(${tax.taxPercentage}%)`;
  }
}
