import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Tile } from '../models/Tile';
import { BackgroundBlock } from '../models/BackgroundBlock';
import { ForgroundBlock } from '../models/ForgroundBlock';
import { Hero } from '../models/Hero';
import { BlockType } from '../models/BlockType';
import { PuzzleService } from '../puzzle-builder-screen/puzzle.service';
import { Puzzle } from '../models/Puzzle';
import { Solution } from '../models/Solution';
import {ToastrService} from "ngx-toastr";
import {ApplicationStatusService} from "../../admin/admin-application-status/application-status.service";

@Component({
  selector: 'app-solve-puzzle',
  templateUrl: './solve-puzzle.component.html',
  styleUrls: ['./solve-puzzle.component.css']
})
export class SolvePuzzleComponent implements OnInit {

  public sandbox : boolean = false;
  public levelGrid : Tile[][];
  private puzzleID : number;
  private className : String= "grid-item-without-border";
  private grid_width : number = 25;
  private grid_height : number = 20;
  private heroesList : Hero[] = new Array<Hero>();
  public isHeroView : boolean = false;
  public isCodeView : boolean = true;
  public isCostView : boolean = false;
  public tab1ClassName : String = "unSelectedTab";
  public tab2ClassName : String = "selectedTab";
  public tab3ClassName : String = "unSelectedTab";
  public solution : Solution;
  public bestSolution : Solution = new Solution();
  public consoleOutput : string[] = [];
  private puzzle : Puzzle;
  public noPuzzle : boolean = false;
  public code:string;
  public attempts: number;
  private submitting : boolean = false;
  public coordsPosition:number = 0;
  public animationIsRunning:boolean = false;
  private isToggled : boolean = false;
  public selectedHero : Hero;
  public millisecond : number = 250;

  constructor(private route: ActivatedRoute,private toastrService: ToastrService,  private puzzleService : PuzzleService, private applicationStatusService:ApplicationStatusService) {

  }

  ngOnInit() {
    this.applicationStatusService.checkStatusForView("production");


    this.route.queryParams
      .subscribe(params => {
        this.puzzleID = params.puzzle
        this.getPuzzle();
        this.getBestSolution();
        this.getAttempts();
      });
  }

  private getBestSolution(updateConsole : boolean = true) {
    this.puzzleService.getBestSolution(this.puzzleID).subscribe(
      data => {
        if(data.id != 0) {
          this.bestSolution = data;
          if (this.solution == null) {
            this.solution = data;
          }
        }
        if(this.solution.code != null && updateConsole) {
          this.consoleOutput = this.solution.consoleOutput.split("\n");
        }
      }
    )
  }

  private getAttempts() {
    this.puzzleService.getAttempts(this.puzzleID).subscribe(
      data => {
        this.attempts = data;
      }
    )
  }

  private checkAttempts() {
    if(this.attempts > 0) {
      return false && this.submitting;
    }
    return true;
  }

  private getPuzzle() {
    this.puzzleService.getPuzzle(this.puzzleID).subscribe(
      puzzle => {
        if(puzzle != null) {
          this.puzzle = puzzle;
          this.heroesList = puzzle.heroes;
          this.puzzleService.costcard = puzzle.costCard;
          this.createGrid2DArray(puzzle)
        } else {
          this.noPuzzle = true;
        }
      }
    )
  }

  private createGrid2DArray(puzzle : Puzzle){
    let levelListIndex = 0;
    let tempGrid : Tile[][] = [];
    for(var i: number = 0; i < this.grid_width; i++) {
      tempGrid[i] = [];
      for(var j: number = 0; j< this.grid_height; j++) {
        tempGrid[i][j] = new Tile(i, j, null, null);
        levelListIndex++;
      }
    }
    for(let entry of puzzle.levelGrid) {
      tempGrid[entry.tile_x][entry.tile_y] = entry
    }
    this.levelGrid = tempGrid;
  }

  public showHeroView(){
    this.tab1ClassName = "selectedTab";
    this.tab2ClassName = "unSelectedTab";
    this.tab3ClassName = "unSelectedTab";
    this.isHeroView = true;
    this.isCodeView = false;
    this.isCostView = false;
  }

  public showCodeView(){
    this.tab1ClassName = "unSelectedTab";
    this.tab2ClassName = "selectedTab";
    this.tab3ClassName = "unSelectedTab";
    this.isHeroView = false;
    this.isCodeView = true;
    this.isCostView = false;
  }

  public showCostView(){
    this.tab1ClassName = "unSelectedTab";
    this.tab2ClassName = "unSelectedTab";
    this.tab3ClassName = "selectedTab";
    this.isHeroView = false;
    this.isCodeView = false;
    this.isCostView = true;
  }

  public selectHero(hero : Hero) {
    this.selectedHero = hero;
  }

  private toggleGridLines(){
    if(this.className == "grid-item"){
      this.className = "grid-item-without-border";
    }else{
      this.className = "grid-item";
    }
  }

  public doTextareaValueChange(ev) {
    this.code = ev.target.value;
  }

  public submitSolution() {
    this.submitting = true;
    this.toastrService.info("Het antwoord is ingedient, het kan enkele seconde duren voor je resultaat binnen is.");

    this.puzzleService.getSolution(this.puzzleID, this.code).subscribe(data => {
      this.solution = data;
      if(this.solution.code != null) {
        if(this.solution.succes) {
          this.toastrService.success("De puzzel is succesvol doorlopen!")
        } else {
          this.toastrService.error("Oei... Je hebt de puzzel niet succesvol doorlopen.")
        }
        this.consoleOutput = this.solution.consoleOutput.split("\n");
      this.getAttempts()
      this.getBestSolution(false);
      this.submitting = false;
    }
  })
  }

  public startAnimation(){
    if(!this.animationIsRunning) {
      this.coordsPosition = 0;
      this.animationIsRunning = true;
      this.runAnimation()
    }
  }

  public continueAnimation(){
    if(!this.animationIsRunning) {
      this.animationIsRunning = true;
      this.runAnimation()
    }
  }
  public stepForwardsInAnimation(){

    if(this.solution == null || this.solution.positions == null){
      return;
    }

    if(this.coordsPosition < this.solution.positions.length - 1) {
      this.coordsPosition++;
    }
  }
  public stepBackwardsInAnimation(){

    if(this.solution == null || this.solution.positions == null){
      return;
    }

    if(this.coordsPosition > 0) {
      this.coordsPosition--;
    }
  }
  public stopAnimation(){
    if(this.animationIsRunning){
      this.animationIsRunning = !this.animationIsRunning;
    }
  }

  public runAnimation(){

    if(this.solution == null || this.solution.positions == null || !this.animationIsRunning){
      return;
    }
    setTimeout( () => {
      if(this.coordsPosition < this.solution.positions.length - 1) {
        this.coordsPosition++;
        this.runAnimation();
      } else {
        this.animationIsRunning = false;
      }
    }, this.millisecond);
  }


  public toggleConsole(){
    if(!this.isToggled){
    document.getElementById("consoleDiv").style.height = "400px";
    document.getElementById("consoleDiv").style.top = "-300px";
    this.isToggled = true;
    }else {
      document.getElementById("consoleDiv").style.height = "100px";
      document.getElementById("consoleDiv").style.top = "0px";
      this.isToggled = false;
    }
  }

  public getSuccesText(succes :boolean){
    if(succes) return "Succes"
    return "Niet gehaald"
  }

}
