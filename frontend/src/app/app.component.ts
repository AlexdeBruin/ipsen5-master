import { Component } from '@angular/core';
import {TokenService} from "./components/login/token/token.service";
import {Router} from "@angular/router";
import {LoginService} from "./components/login/login.service";
import { AdminApplicationStatusService } from "./components/admin/admin-application-status/admin-application-status.service";
import {ApplicationStatusService} from "./components/admin/admin-application-status/application-status.service";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  private status:string;

  constructor(private router : Router,
              public loginService: LoginService,
              public tokenService: TokenService,
              private statusService: ApplicationStatusService,
              private adminStatusService: AdminApplicationStatusService ) { }


  ngOnInit() {
    this.tokenService.isValid();

  }


}
