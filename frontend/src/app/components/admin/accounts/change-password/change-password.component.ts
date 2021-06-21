import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { AccountService } from '../../account.service';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { Account } from '../../account';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  account: Account;
  accountForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private accountService: AccountService,
    private fb: FormBuilder
  ) {
    this.createForm();
  }

  ngOnInit() {
    this.accountService.getAccountByName(
      this.route.snapshot.paramMap.get('name')
    ).subscribe(
      account => this.account = account
    );
  }

  private createForm() {
    this.accountForm = this.fb.group({
      password: ['', [
        Validators.required,
        Validators.minLength(8)
      ]],
      passwordAgain: ['', [
        Validators.required
      ]]
    });
  }

  onSubmit() {
    const accountWithNewPassword = this.prepareNewAccount();
    this.accountService.updateAccountByName(
      accountWithNewPassword.username,
      accountWithNewPassword
    ).subscribe(
      () => this.router.navigate(['/admin/accounts'])
    );
  }

  prepareNewAccount(): Account {
    const newAccount = Object.assign({}, this.account);

    newAccount.password = this.accountForm.get('password').value;

    return newAccount;
  }

}
