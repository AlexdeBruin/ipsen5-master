import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Account } from '../../account';
import { AccountService } from '../../account.service';
import { makeAccountRole } from '../../../shared/role-utils';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css']
})
// TODO properly use reactive forms
export class CreateAccountComponent implements OnInit {
  accountForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private accountService: AccountService,
    private toastr: ToastrService
  ) {
    this.createForm();
  }

  ngOnInit() {
  }

  onSubmit() {
    this.accountService.addAccount(this.prepareAccount()).subscribe(
      () => this.toastr.success('Account toegevoegd')
    );
  }

  prepareAccount(): Account {
    const account: Account = {
      username: this.username(),
      password: this.password(),
      accountRole: makeAccountRole(this.accountRole()),
      score: null,
      active: true,
      requiresPasswordChange: true,
      accountId: null,
      puzzle: null
    };

    this.accountForm.reset();

    return account;
  }

  private username(): string {
    return this.getFromValueOrEmptyString('username');
  }

  private password(): string {
    return this.getFromValueOrEmptyString('password');
  }

  private accountRole(): string {
    return this.getFromValueOrEmptyString('accountRole');
  }

  private passwordAgain(): string {
    return this.getFromValueOrEmptyString('passwordAgain');
  }

  public passwordsMatch(): boolean {
    return this.password() === this.passwordAgain();
  }

  passwordTooShort(): boolean {
    return this.getFromValueOrEmptyString('password').length < 8;
  }

  private getFromValueOrEmptyString(field: string): string {
    return this.accountForm.get(field).value === null ?
      '' : this.accountForm.get(field).value;
  }

  private createForm() {
    this.accountForm = this.fb.group({
        username: ['', [
          Validators.required
        ]
        ],
        password: ['', [
          Validators.required,
          Validators.minLength(8)
        ]
        ],
        passwordAgain: ['', [
          Validators.required
        ]
        ],
        accountRole: ['', [
          Validators.required
        ]
        ],
      }
    );
  }
}
