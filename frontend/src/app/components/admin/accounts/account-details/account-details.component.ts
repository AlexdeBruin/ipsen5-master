import { Component, Input, OnInit } from '@angular/core';
import { Account } from '../../account';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.css']
})
export class AccountDetailsComponent implements OnInit {
  @Input() account: Account;

  constructor() { }

  ngOnInit() {
  }

}
