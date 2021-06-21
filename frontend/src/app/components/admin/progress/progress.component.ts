import {Component, OnInit} from '@angular/core';
import {AccountService} from "../account.service";
import {Account} from "../account";
import {ProgressService} from "./progress.service";


@Component({
  selector: 'app-progress',
  templateUrl: './progress.component.html',
  styleUrls: ['./progress.component.css']
})

export class ProgressComponent implements OnInit {
  constructor(private accountService: AccountService, private progressService: ProgressService) { }

  public accountList : Account[] = [];
  public displayedList : displayedItem[] = [];
  displayedColumns = ['username', 'submitted'];
  public selectedItem : displayedItem;
  selectedAccount: Account;
  totalSolvedPuzzle = 0;

  ngOnInit() {
     this.accountService.fetchAllAccounts().subscribe(data =>{
       this.accountList = data;
      data.forEach((account) => {
        if (account.puzzle === null){
          this.displayedList.push({username: account.username, submitted: false});
        }else{
          this.displayedList.push({username: account.username, submitted: true});
        }
      })
    });
  }


  onSelect(item){
    this.selectedItem = item;
    this.accountList.forEach((account) => {
      if(account.username === this.selectedItem.username){
        this.selectedAccount = account;
    }});
    this.progressService.getAmountSolved(this.selectedAccount.accountId).subscribe(data => {
      this.totalSolvedPuzzle = data;
    })
  }

  deletePuzzle() {
    this.progressService.deletePuzzle(this.selectedAccount.accountId).subscribe();
  }
}

export interface displayedItem {
  username: string;
  submitted: boolean;
}



