import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Account } from '../admin/account';


@Injectable({
  providedIn: 'root'
})
export class LoginService {
  loggedInAccount: Account;

  tryLogin(body): Observable<Object> {
    return this.http.post('api/trylogin', body);
  }

  getUser(): Observable<Account> {
    return this.http.get<Account>('api/getaccount');
  }

  tryValidateToken(body) {
    return this.http.post('api/validateToken', body);
  }

  changePassword(body) {
    return this.http.post('api/changePassword', body);
  }

  constructor(private http: HttpClient) {
  }
}
