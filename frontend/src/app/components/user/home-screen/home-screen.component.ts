import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {ApplicationStatusService} from "../../admin/admin-application-status/application-status.service";
import {LoginService} from "../../login/login.service";

@Component({
  selector: 'app-home-screen',
  templateUrl: './home-screen.component.html',
  styleUrls: ['./home-screen.component.css']
})
export class HomeScreenComponent implements OnInit {

  private closed: boolean = false;
  private production: boolean = false;
  private sandbox: boolean = false;

  constructor(private rout : Router,
              private statusService: ApplicationStatusService,
              private loginService: LoginService) { }

  ngOnInit() {
  }

  public isClosed() {
    return this.statusService.isClosed();
  }

  public isProduction() {
    return this.statusService.isProduction();
  }

  public isSandbox() {
    return this.statusService.isSandbox();
  }

  public isAdmin() {
    this.loginService.loggedInAccount.accountRole.roleName === 'ADMIN';
  }

}
