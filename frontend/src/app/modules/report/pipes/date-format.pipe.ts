import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateFormat'
})
export class DateFormatPipe implements PipeTransform {

  transform(value: Date): string {
    if(value){;
      const day = this.padZero(value.getDate());
      const month = this.padZero(value.getMonth() + 1); // ¡Ojo! Los meses en JavaScript son de 0 a 11
      const year = this.padZero(value.getFullYear() % 100);

      return `${day}/${month}/${year}`;
    }
    return '';
  }

  padZero(value: number):string {
    return value < 10 ? `0${value}`:`${value}`;
  }

}
