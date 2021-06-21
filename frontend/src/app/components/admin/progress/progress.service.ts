import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Rx";

@Injectable({
  providedIn: 'root'
})
export class ProgressService {

   getAmountSolved(accountId: number): Observable<any>{
    return this.http.get("api/progress/amountsolved/" + accountId);
  }

  deletePuzzle(accountId: number): Observable<any> {
    return this.http.delete("api/progress/deletepuzzle/" + accountId)
  }
  constructor(private http: HttpClient) { }
}
