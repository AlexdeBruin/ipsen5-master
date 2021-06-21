import {Component, Input, OnInit} from '@angular/core';
import {Costcard} from "../models/Costcard";
import {PuzzleService} from "../puzzle-builder-screen/puzzle.service";

@Component({
  selector: 'app-cost-card',
  templateUrl: './cost-card.component.html',
  styleUrls: ['./cost-card.component.css']
})
export class CostCardComponent implements OnInit {


 @Input()isBuilding: boolean = false;
  public costCard : Costcard;

  constructor(public puzzleService :PuzzleService) { }

  ngOnInit() {
    if (localStorage.getItem("costCard" ) == null) {
      //this.puzzleService.costcard = new Costcard();
    } else {
      this.puzzleService.costcard = JSON.parse(localStorage.getItem("costCard"));
    }

  }


  public saveLocaly() {
      localStorage.setItem("costCard", JSON.stringify(this.puzzleService.costcard))
  }
}
