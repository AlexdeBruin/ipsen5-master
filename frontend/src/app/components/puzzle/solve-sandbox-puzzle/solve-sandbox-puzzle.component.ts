import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PuzzleService } from '../puzzle-builder-screen/puzzle.service';
import { Solution } from '../models/Solution';
import { Puzzle } from '../models/Puzzle';
import { Tile } from '../models/Tile';
import { Hero } from '../models/Hero';
import { log } from 'util';
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-solve-sandbox-puzzle',
  templateUrl: './../solve-puzzle/solve-puzzle.component.html',
  styleUrls: ['./../solve-puzzle/solve-puzzle.component.css']
})
export class SolveSandboxPuzzleComponent implements OnInit {

  public sandbox : boolean = true;
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
  public solution : Solution = new Solution();
  public bestSolution : Solution = new Solution();
  public consoleOutput : string[] = [];
  private puzzle : Puzzle;
  public noPuzzle : boolean = false;
  public code:string;
  public attempts: number = 9001;
  private submitting : boolean = false;
  public coordsPosition:number = 0;
  public animationIsRunning:boolean = false;
  private isToggled : boolean = false;
  public millisecond : number = 250;

  constructor(private route: ActivatedRoute, private toastrService: ToastrService,private puzzleService : PuzzleService) { }

  ngOnInit() {
    this.route.queryParams
      .subscribe(params => {
        this.puzzleID = params.puzzle
        this.getPuzzle();
      });
  }

  private getPuzzle() {
    this.puzzleService.getPuzzle(this.puzzleID).subscribe(
      puzzle => {
        if(puzzle != null) {
          this.puzzle = puzzle;
          this.heroesList = puzzle.heroes;
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

  private getAttempts() {
    return this.attempts;
  }

  public doTextareaValueChange(ev) {
    this.code = ev.target.value;
  }

  private checkAttempts() {
    if(this.attempts > 0) {
      return false && this.submitting;
    }
    return true;
  }

  public submitSolution() {
    this.submitting = true;
    this.puzzleService.getSandboxSolution(this.puzzleID, this.code).subscribe(data => {
      this.solution = data;
      if(this.solution.code != null) {
        if(this.solution.succes) {
          this.toastrService.success("De puzzel is succesvol doorlopen!")
        } else {
          this.toastrService.error("Oei... Je hebt de puzzel niet succesvol doorlopen.")
        }
        this.consoleOutput = this.solution.consoleOutput.split("\n");
        this.bestSolution.score = this.solution.score;
        this.bestSolution.succes = this.solution.succes;
      }
      this.submitting = false;
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

  private toggleGridLines(){
    if(this.className == "grid-item"){
      this.className = "grid-item-without-border";
    }else{
      this.className = "grid-item";
    }
  }

  public getSuccesText(succes :boolean){
    if(succes) return "Succes"
    return "Niet gehaald"
  }
}
