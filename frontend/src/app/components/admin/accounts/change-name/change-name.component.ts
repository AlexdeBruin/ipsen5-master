import { Component, OnInit } from '@angular/core';
import { Account } from '../../account';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from '../../account.service';

@Component({
  selector: 'app-change-name',
  templateUrl: './change-name.component.html',
  styleUrls: ['./change-name.component.css']
})
export class ChangeNameComponent implements OnInit {
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
      newUsername: ['', [
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

    newAccount.username = this.accountForm.get('newUsername').value;

    return newAccount;
  }
}
