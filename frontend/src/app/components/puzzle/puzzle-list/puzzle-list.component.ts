import { Component, OnInit } from '@angular/core';
import { Puzzle } from '../models/Puzzle';
import { PuzzleService } from '../puzzle-builder-screen/puzzle.service';
import { Account } from '../../admin/account';
import { PuzzleListModel } from '../models/PuzzleListModel';
import {ApplicationStatusService} from "../../admin/admin-application-status/application-status.service";

@Component({
  selector: 'app-puzzle-list',
  templateUrl: './puzzle-list.component.html',
  styleUrls: ['./puzzle-list.component.css']
})
export class PuzzleListComponent implements OnInit {

  solvedPercentage: number = 0;
  numbers: number[];
  todo: Account[];
  tried: Account[];
  done: Account[];
  doneNoAttemptsLeft: Account[];
  fail: Account[];
  puzzleList : PuzzleListModel;


  constructor(private puzzleService : PuzzleService, private applicationStatusService:ApplicationStatusService) {

  }

  ngOnInit() {
    this.applicationStatusService.checkStatusForView("production");

    this.puzzleService.getPuzzleList().subscribe(
      data => {
        this.puzzleList = data;
        this.todo = data.todo;
        this.tried = data.tried;
        this.done = data.done;
        this.fail = data.fail;
        this.doneNoAttemptsLeft = data.doneNoAttemptsLeft;

        let allpuzzles = this.todo.length + this.tried.length + this.done.length + this.fail.length + this.doneNoAttemptsLeft.length;
        this.solvedPercentage = (this.done.length + this.doneNoAttemptsLeft.length) / allpuzzles * 100;
        this.solvedPercentage = Math.round(this.solvedPercentage);

        if(allpuzzles == 0) {
          this.solvedPercentage = 0;
        }

        let max = Math.max(this.todo.length, this.tried.length, this.done.length, this.fail.length, this.doneNoAttemptsLeft.length);
        this.numbers = Array(max).fill(0).map((x,i)=>i);
      }
    )
  }

  public hasList() {
    return this.puzzleList != null;
  }

  public isListElement(element : Account) {
    return element != null;
  }

}
