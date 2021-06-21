import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Account } from '../../account';
import { AccountService } from '../../account.service';
import { Role } from '../../../shared/role';
import { makeAccountRole, roleIdFromRoleName } from '../../../shared/role-utils';
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-bulk-account-creation',
  templateUrl: './bulk-account-creation.component.html',
  styleUrls: ['./bulk-account-creation.component.css']
})
export class BulkAccountCreationComponent implements OnInit {
  bulkAccountCreationForm: FormGroup;
  hasAddedAccounts = false;
  accountsToBeAdded: Account[];

  constructor(
    private fb: FormBuilder,
    private accountService: AccountService,
    private toastrService: ToastrService
  ) {
    this.createForm();
  }

  ngOnInit() {
  }

  private createForm() {
    this.bulkAccountCreationForm = this.fb.group({
      amount: [0, [
        Validators.required,
        Validators.min(1),
        Validators.max(100)
      ]],
      accountRole: ['', [
        Validators.required
      ]],
      baseName: ['', [
        Validators.required
      ]]
    });
  }

  onSubmit() {
    this.accountsToBeAdded = this.makeAccountArray();
    this.accountService.addMultipleAccounts(this.accountsToBeAdded)
        .subscribe(() => {
          this.hasAddedAccounts = true
          this.toastrService.success("De accounts zijn aangemaakt!");
        });
  }

  makeAccountArray(): Account[] {
    return this.range(this.amountOfAccountsToAdd()).map(
      i => this.newAccount(i)
    );
  }

  newAccount(accountNumber: number): Account {
    return {
      username: `${this.baseNameWithNumber(accountNumber)}`,
      password: this.somewhatRandomEightCharacterPassword(),
      accountRole: makeAccountRole(this.accountRole()),
      score: null,
      active: true,
      requiresPasswordChange: true,
      accountId: null,
      puzzle: null
    };
  }

  baseNameWithNumber(accountNumber: number): string {
    return `${this.baseName()}${accountNumber + 1}`;
  }

  baseName(): string {
    return this.bulkAccountCreationForm.get('baseName').value;
  }

  amountOfAccountsToAdd(): number {
    return this.bulkAccountCreationForm.get('amount').value;
  }

  accountRole(): string {
    return this.bulkAccountCreationForm.get('accountRole').value;
  }


  somewhatRandomEightCharacterPassword(): string {
    return Math.random().toString(36).substring(2, 10);
  }

  // JS does not have this, lodash does, I am not adding lodash just for this
  range(length: number): number[] {
    return Array.from(new Array(length), (x, i) => i);
  }

}
