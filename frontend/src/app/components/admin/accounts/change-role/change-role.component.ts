import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Account } from '../../account';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from '../../account.service';
import { makeAccountRole } from '../../../shared/role-utils';

@Component({
  selector: 'app-change-role',
  templateUrl: './change-role.component.html',
  styleUrls: ['./change-role.component.css']
})
export class ChangeRoleComponent implements OnInit {
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
      role: ['', [
        Validators.required,
      ]],
    });
  }

  onSubmit() {
    const accountWithNewRole = this.prepareNewAccount();
    this.accountService.updateAccountByName(
      accountWithNewRole.username,
      accountWithNewRole
    ).subscribe(
      () => this.router.navigate(['/admin/accounts'])
    );
  }

  prepareNewAccount(): Account {
    const newAccount = Object.assign({}, this.account);

    newAccount.accountRole = makeAccountRole(this.accountForm.get('role').value);

    return newAccount;
  }
}
