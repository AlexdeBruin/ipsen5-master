import { Component, OnInit } from '@angular/core';
import {AccountService} from "../../admin/account.service";
import {Account} from "../../admin/account";
import {Score} from "../../admin/score";
import {TableScore} from "./TableScore";

@Component({
  selector: 'app-highscore',
  templateUrl: './highscore.component.html',
  styleUrls: ['./highscore.component.css']
})
export class HighscoreComponent implements OnInit {

  public accountList : Account[] = [];
  public scoreList : TableScore[] = [];
  public totalScore : number[] = new Array();

  constructor(private accountService : AccountService) { }

  ngOnInit() {

    this.accountService.fetchAllAccounts().subscribe(data =>{
      this.accountList = data;
      this.dataFetched();
    });
  }

  private dataFetched(){

    for(let i = 0; i < this.accountList.length; i++){
      this.scoreList.push(new TableScore(this.accountList[i].username, this.accountList[i].score.scoreARelativeScore + this.accountList[i].score.scoreASolutionRanking + this.accountList[i].score.scoreASolutionVariation, this.accountList[i].score.scoreBleftEnergy + this.accountList[i].score.scoreBSolutionScore * 2))
    }
    this.sortList()

  }

  public getAccount(pos) : TableScore{
    if(pos > this.scoreList.length){
      return null;
    }
    return this.scoreList[pos];
  }

  private sortList(){
    let n : number = this.scoreList.length;
    let temp : TableScore = null;
    for(let i=0; i < n; i++){
      for(let j=1; j < (n-i); j++){
        if(this.scoreList[j-1].totaalScore < this.scoreList[j].totaalScore){
          //swap elements
          temp = this.scoreList[j-1];
          this.scoreList[j-1] = this.scoreList[j];
          this.scoreList[j] = temp;
        }
      }
    }
  }

  public countListItem(item){
    return this.scoreList.indexOf(item);
  }

}
