import { Component, OnInit } from '@angular/core';
import { TokenService } from '../../login/token/token.service';
import { LoginService } from '../../login/login.service';
import { Router } from '@angular/router';
import { ApplicationStatusService} from '../../admin/admin-application-status/application-status.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {


  constructor(
    public tokenService: TokenService,
    public loginService: LoginService,
    private statusService: ApplicationStatusService,
    private router: Router,
  ) {
    this.ngOnInit();
  }

  ngOnInit() {
  }

  private logOut() {
    this.tokenService.setIsLoggedIn(false);
    this.tokenService.deleteToken();
  }

  private isOnAdmin(): boolean {
    return this.router.url.startsWith('/admin');
  }

  getLoggedInUsernameOrNull() {
    return this.loginService.loggedInAccount ?
      this.loginService.loggedInAccount.username : null;
  }

  isLoggedIn() {
    return this.tokenService.isLoggedIn;
  }

  isAdmin() {
    return this.loginService.loggedInAccount !== undefined &&
      this.loginService.loggedInAccount.accountRole.roleName === 'ADMIN';
  }

  isClosed () {
    return this.statusService.isClosed();
  }

  isProduction(){
    return this.statusService.isProduction();
  }

  isSandbox(){
    return this.statusService.isSandbox();
  }

}
