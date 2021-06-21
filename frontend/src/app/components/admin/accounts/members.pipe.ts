import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'members'
})
export class MembersPipe implements PipeTransform {

  transform(value: string[], args?: any): string {
    return value === null ? 'n.v.t.' : value.join(', ');
  }

}
