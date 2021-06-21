import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Credentials } from '../credentials';
import { LoginService } from '../login.service';
import { HttpErrorResponse } from '@angular/common/http';
import { TokenService } from '../token/token.service';
import {ToastrService} from "ngx-toastr";


@Component({
  selector: 'app-login-screen',
  templateUrl: './login-screen.component.html',
  styleUrls: ['./login-screen.component.css']
})
export class LoginScreenComponent implements OnInit {

  constructor(private router: Router, private toastrService: ToastrService, public loginService: LoginService, private tokenservice: TokenService) {
  }

  credentials: Credentials = new Credentials();

  ngOnInit() {
    this.checkIsLoggedin();
  }


  public checkIsLoggedin() {
    if (this.tokenservice.getIsloggedIN()) {
      this.router.navigate(['/home']);
    }
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEventUp(event: KeyboardEvent) {
    if (event.key == 'Enter') {
      this.tryLogin();
    }
  }

  public tryLogin(): void {
    const body = { username: this.credentials.username, password: this.credentials.password };

    this.tokenservice.deleteToken();
    this.loginService.tryLogin(body).subscribe(data => {
      const token = data['token'];
      this.tokenservice.setToken(token);
      this.tokenservice.isValid();
      this.createLoggedInUser();
      this.toastrService.success("Succesvol ingelogd")
    }, (error: any) => {
      if (error instanceof HttpErrorResponse) {
        if (error.status === 403) {
          this.showLoginError();
        }
      }
    });
  }

  private showLoginError() {
      this.toastrService.error("De inloggevens zijn incorrect");
  }

  private createLoggedInUser(): void {
    this.loginService.getUser().subscribe(data => {
      this.loginService.loggedInAccount = data;
      if (data.requiresPasswordChange) {
        this.router.navigate(['/forcepassword']);
      } else {
        this.router.navigate(['/home']);
      }
    });

  }
}
