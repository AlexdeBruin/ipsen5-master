import {Component, HostListener, OnInit} from '@angular/core';
import { PasswordChange } from '../passwordChange';
import { LoginService } from '../login.service';
import { TokenService } from '../token/token.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-password-change',
  templateUrl: './password-change.component.html',
  styleUrls: ['./password-change.component.css']
})
export class PasswordChangeComponent implements OnInit {

  constructor(private loginService: LoginService, private tokenService: TokenService, private router: Router) {
  }

  changePassword: PasswordChange = new PasswordChange();

  ngOnInit() {
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEventUp(event: KeyboardEvent) {
    if (event.key == 'Enter') {
      this.tryChangePassword();
    }
  }

  public tryChangePassword() {
    if (this.changePassword.newPassword.length >= 8) {
      if (this.changePassword.newPassword === this.changePassword.repeatNewPassword) {
        this.loginService.changePassword(this.changePassword.newPassword).subscribe(data => {
          this.loginService.getUser().subscribe(data => {
            this.loginService.loggedInAccount = data;
            this.router.navigate(['/home']);
          });
        });
      } else {
        alert('Wachtwoorden komen niet overeen');
      }
    } else {
      alert('Wachtwoord moet minimaal acht tekens lang zijn');
    }
  }
}
