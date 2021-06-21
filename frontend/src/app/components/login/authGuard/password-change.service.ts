import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {TokenService} from "../token/token.service";
import {LoginService} from "../login.service";
import {isUndefined} from "util";

@Injectable({
  providedIn: 'root'
})

@Injectable()
export class PasswordChangeService implements CanActivate {

  constructor(private tokenservice: TokenService, private router: Router, private loginService:LoginService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    let url: string = state.url;
    if (this.tokenservice.getIsloggedIN()) {
      if ( isUndefined(this.loginService.loggedInAccount) || !this.loginService.loggedInAccount.requiresPasswordChange){
        this.router.navigate(['home']);
        return false;
      }
      return true;
    }
    this.router.navigate(['home']);
    return false;
  }
}

