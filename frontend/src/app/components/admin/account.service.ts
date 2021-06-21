import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Account } from './account';
import { catchError, map, tap } from 'rxjs/operators';
import {ToastrService} from "ngx-toastr";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private accountsUrl = 'api/accounts';

  constructor(private http: HttpClient, private toastrService:ToastrService) {
  }

  addAccount(account: Account): Observable<void> {
    return this.http.post<void>(this.accountsUrl, account, httpOptions).pipe(
      catchError(this.handleError<void>('addAccount'))
    );
  }

  addMultipleAccounts(accounts: Account[]): Observable<void> {
    const url = `${this.accountsUrl}/multiple`;
    return this.http.post<void>(url, accounts, httpOptions).pipe(
      catchError(this.handleError<void>('addMultipleAccounts'))
    );
  }

  getAccountByName(name: string): Observable<Account> {
    const url = `${this.accountsUrl}/${name}`;
    return this.http.get<Account>(url)
               .pipe(
                 map(account => account),
                 tap(p => {
                   const outcome = p ? 'fetched' : 'did not find';
                 }),
                 catchError(this.handleError<Account>(`getAccount name = ${name}`))
               );
  }

  updateAccountByName(name: string, account: Account): Observable<any> {
    const url = `${this.accountsUrl}/name`;
    return this.http.put(url, account, httpOptions).pipe(
      catchError(this.handleError<Account>(`updateAccount name=${name}`))
    );
  }


  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      this.toastrService.error("Er is iets mis gegaan met de actie: " + operation);
      return of(result as T);
    };
  }

  public fetchAllAccounts() {
    return this.http.get<Account[]>('api/accounts/allAccounts');
  }

}
