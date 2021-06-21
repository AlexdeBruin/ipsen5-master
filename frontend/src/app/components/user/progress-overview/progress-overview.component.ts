import {Component, Input, OnInit} from '@angular/core';
import {AccountService} from "../../admin/account.service";
import {Account} from "../../admin/account";

@Component({
  selector: 'app-progress-overview',
  templateUrl: './progress-overview.component.html',
  styleUrls: ['./progress-overview.component.css']
})
export class ProgressOverviewComponent implements OnInit {

  @Input() accountName:string;
  public account:Account;

  constructor(private accountService:AccountService) { }

  ngOnInit() {
    this.accountService.getAccountByName(this.accountName).subscribe(data => {
      this.account = data;
    });
  }

}
