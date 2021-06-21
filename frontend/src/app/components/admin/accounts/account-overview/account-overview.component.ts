import { Component, OnInit } from '@angular/core';
import { Account } from '../../account';
import { AccountService } from '../../account.service';

@Component({
  selector: 'app-account-overview',
  templateUrl: './account-overview.component.html',
  styleUrls: ['./account-overview.component.css']
})
export class AccountOverviewComponent implements OnInit {
  accounts: Account[];
  selectedAccount: Account;

  constructor(private accountService: AccountService) {
  }

  ngOnInit() {
    this.getAccounts();
  }

  private getAccounts() {
    this.accountService.fetchAllAccounts().subscribe(
      accounts => this.accounts = accounts
    );
  }

  selectNewAccount(account) {
    this.selectedAccount = account;
  }

}
