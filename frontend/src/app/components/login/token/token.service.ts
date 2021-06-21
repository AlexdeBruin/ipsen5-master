import { Injectable } from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient, HttpErrorResponse, HttpResponse} from "@angular/common/http";

import {Observable} from "rxjs/";
import {LoginService} from "../login.service";
import {ApplicationStatusService} from "../../admin/admin-application-status/application-status.service";
import {AdminApplicationStatusService} from "../../admin/admin-application-status/admin-application-status.service";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor(private http: HttpClient,private adminStatusService: AdminApplicationStatusService , private statusService: ApplicationStatusService ,private loginService: LoginService, private router: Router){}
  public isLoggedIn = false;

  public setToken(token): void{
    this.isLoggedIn = true;
    localStorage.setItem("token", token)
  }

  public deleteToken(): void{
    this.isLoggedIn = false;
    localStorage.removeItem("token")
  }

  public isValid(){
    let token = this.getToken();
    if(token === null){
      this.isLoggedIn = false;
      return false;
    }
    this.loginService.tryValidateToken(token).subscribe(data => {
      let code = data['message'];
      this.loginService.getUser().subscribe(data => {
        if (code === "1") {
          this.getStatus();
          this.loginService.loggedInAccount = data;
          this.isLoggedIn = true;
        } else {
          this.isLoggedIn = false;
          this.deleteToken();
        }
      })
      },
    );
    return false;
  }


  private getStatus() {
    this.adminStatusService.getStatus().subscribe(
      data => {
        this.statusService.setStatus(data);

      });
  }

  getToken(): String {
    return localStorage.getItem("token")
  }

  getIsloggedIN(): boolean{
    return this.getToken() != null;
  }

  setIsLoggedIn(isLoggedIn): void{
    this.isLoggedIn = isLoggedIn;
    this.deleteToken();
    if(!this.isLoggedIn){
      this.router.navigate([''])
    }
  }
}
