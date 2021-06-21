import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import { Observable } from 'rxjs';
import { TokenService } from './token/token.service';
import { LoginService } from './login.service';

@Injectable()
export class AdminGuard implements CanActivate {

  constructor(
    private tokenService: TokenService,
    private loginService: LoginService,
    private router: Router
  ) {
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (this.tokenService.getIsloggedIN()) {
      if(!(this.loginService.loggedInAccount !== undefined &&
        this.loginService.loggedInAccount.accountRole.roleName === 'ADMIN')){
        this.router.navigate(['home']);
        return false;
      }
      return true;
    } else {
      return false;
    }
  }
}
