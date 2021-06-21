import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'accountRole'
})
export class AccountRolePipe implements PipeTransform {
  transform(value: string, args?: any): string {
    if (value === 'ADMIN') {
      return 'Administrator';
    } else if (value === 'GROUP') {
      return 'Groep';
    } else {
      return 'Dit zou niet moeten gebeuren';
    }
  }

}
