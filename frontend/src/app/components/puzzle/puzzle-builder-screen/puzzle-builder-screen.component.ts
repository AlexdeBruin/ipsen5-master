import {Component, HostListener, Inject, OnInit} from '@angular/core';
import {Tile} from "../models/Tile";
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';

import {BackgroundBlock} from "../models/BackgroundBlock";
import {ForgroundBlock} from "../models/ForgroundBlock";
import {Hero} from "../models/Hero";
import {Puzzle} from "../models/Puzzle";
import {PuzzleService} from "./puzzle.service";
import {TileMap} from "../models/TileMap";
import {BlockType} from "../models/BlockType";
import {Solution} from "../models/Solution";
import {PuzzleCode} from "./PuzzleCode";
import {LoginService} from "../../login/login.service";
import {ToastrService} from "ngx-toastr";
import {ApplicationStatusService} from "../../admin/admin-application-status/application-status.service";

@Component({
  selector: 'app-puzzle-builder-screen',
  templateUrl: './puzzle-builder-screen.component.html',
  styleUrls: ['./puzzle-builder-screen.component.css']
})
export class PuzzleBuilderScreenComponent implements OnInit {

  private backgroundList : BackgroundBlock[] = new Array<BackgroundBlock>();
  private forgroundList : ForgroundBlock[] = new Array<ForgroundBlock>();
  private heroesList : Hero[] = new Array<Hero>();
  private selectedHeroList : Hero[] = new Array<Hero>();
  public levelGrid : Tile[][];
  private currentForgroundBlock : ForgroundBlock;
  private currentBackgroundBlock : BackgroundBlock;
  private removeBlock : ForgroundBlock = new ForgroundBlock("/assets/Resources/img/tiles/transparent_block.png", 7, BlockType.REMOVEBLOCK);
  private grid_width : number = 25;
  private grid_height : number = 20;
  public isBlockView : boolean = true;
  public isCodeView : boolean = false;
  public isCostView : boolean = false;
  public puzzleIsSend : boolean = false;

  private className : String= "grid-item";
  public tab1ClassName : String = "selectedTab";
  public tab2ClassName : String = "unSelectedTab";
  public tab3ClassName : String = "unSelectedTab";
  public toolTipClassName : String = "notSelected";
  public toolTipTextClassName : String = "notSelectedText";
  private hasTooltip : boolean = false;
  private keyPressed : String;
  public code:string = "";
  public errors: String[];
  public consoleOutput:String[];
  private isToggled : boolean = false;
  public millisecond : number = 250;
  public solution:Solution;
  public coordsPosition:number = 0;
  public animationIsRunning:boolean = false;

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEventUp(event: KeyboardEvent) {
    this.keyPressed = event.key;
  }

  @HostListener('document:keyup', ['$event'])
  handleKeyboardEventDown(event: KeyboardEvent) {
    this.keyPressed = event.key + "up";
  }

  constructor(public puzzleService:PuzzleService,
              private toastrService: ToastrService,
              public loginService:LoginService,
              public dialog: MatDialog,
              private applicationStatusService:ApplicationStatusService) {
    this.levelGrid = [];
  }


  ngOnInit() {
    this.applicationStatusService.checkStatusForView("production");


    this.fetchSavedLevel();

    this.fetchBackgroundBlocks();

    this.fetchForgroundBlocks();

    this.fetchHeroes()
  }



  private fetchSavedLevel(){
    this.puzzleService.getMySolution().subscribe(data => {
      let puzzle =  data;
      if(puzzle != null){
        this.createGrid(puzzle);
        this.puzzleIsSend = true;
      } else if (localStorage.getItem("grid") != null) {
        this.levelGrid = JSON.parse(localStorage.getItem("grid"));
        this.code = JSON.parse(localStorage.getItem("code"));
      } else {
        this.createGrid2DArray();
      }
    })
  }

  private fetchBackgroundBlocks(){
    this.puzzleService.getAllBackgroundBlocks().subscribe(data => {
      this.backgroundList = data;

    });
  }

  private fetchForgroundBlocks(){
    this.puzzleService.getAllForgroundBlocks().subscribe(data => {
      this.forgroundList = data;
    });
  }

  private fetchHeroes(){
    this.puzzleService.getAllHeroes().subscribe(data => {
      this.heroesList = data;
    })
  }

  private createGrid(puzzle){
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




  public savePuzzle(test:boolean) {
    if(test){
        let confirmSaving = confirm("Weet je zeker dat je de puzzel wil inleveren?");
        if(!confirmSaving){
          return;
        }
        let confirmSaving2 = prompt("Schrijf 'puzzel inleveren' in het invoer veld");
        if(confirmSaving2 != "puzzel inleveren"){
          this.toastrService.warning("De puzzel wordt niet opgeslagen")
          return;
        }
        this.toastrService.info("De puzzel wordt nu opgeslagen, dit kan een paar seconde duren")

    }
    let tiles: Tile[] = [];
    for (let i = 0; i < this.grid_width; i++) {
      for (let j = 0; j < this.grid_height; j++) {
        tiles.push(this.levelGrid[i][j]);
      }
    }
    let puzzle = new Puzzle(tiles, this.puzzleService.getCostCard());
    puzzle.heroes = this.selectedHeroList;
    if(test) {
      this.puzzleService.savePuzzle(new PuzzleCode(puzzle, this.code)).subscribe(data => {
        this.errors = data["errors"];
        this.consoleOutput = this.errors;

        if(this.errors == null || this.errors.length == 0){
          this.toastrService.success("De puzzel is succes opgeslagen!")
        } else {
          this.toastrService.error("Er zitten fouten in je puzzel, los deze op voordat je hem opslaat!")
        }

        if(this.solution != null){
          this.consoleOutput = this.solution.consoleOutput.split("\n");
        }
      });

    } else {
      this.puzzleService.getTestSolution(new PuzzleCode(puzzle, this.code)).subscribe(data => {
        this.errors = data["errors"];
        this.solution = data["solution"];

        this.consoleOutput = this.errors;

        if(this.solution != null){
          this.consoleOutput = this.solution.consoleOutput.split("\n");
        }
      });
    }
  }


  private createGrid2DArray() {
    let levelListIndex = 0;

    for (var i: number = 0; i < this.grid_width; i++) {
      this.levelGrid[i] = [];
      for (var j: number = 0; j < this.grid_height; j++) {
        this.levelGrid[i][j] = new Tile(i, j, null, null);
        levelListIndex++;
      }
    }
  }

  private changeTile(tile) {
    let x = tile.tile_x;
    let y = tile.tile_y;

    if (this.currentBackgroundBlock != null) {
      this.levelGrid[x][y].backgroundBlock = this.currentBackgroundBlock;

    }
    else if (this.currentForgroundBlock != null) {
      if (this.currentForgroundBlock.type === BlockType.REMOVEBLOCK) {
        this.levelGrid[x][y].forgroundBlock = null;
        this.levelGrid[x][y].backgroundBlock = null;
        this.levelGrid[x][y].tileMap = null;
      } else {
        this.levelGrid[x][y].forgroundBlock = this.currentForgroundBlock;
        if (this.currentForgroundBlock.tileMap != null) {
          let tilemap: TileMap = new TileMap();
          this.currentForgroundBlock.tileMap.values.forEach(function (keypair) {
            tilemap.add(keypair.name, keypair.value)
          });
          this.levelGrid[x][y].tileMap = tilemap;
        }
      }
    }
    this.saveToLocalStorage();

  }

  public saveToLocalStorage(){
    localStorage.setItem("grid", JSON.stringify(this.levelGrid));
  }

  public updateForgroundBlock(forgroundBlock){
    if(this.hasTooltip){
      this.toggleTooltip();
      this.hasTooltip = false;
    }
    this.currentForgroundBlock = forgroundBlock;
    this.currentBackgroundBlock = null;
  }

  public updateBackgroundBlock(backgroundBlock){
    if(this.hasTooltip){
      this.toggleTooltip();
      this.hasTooltip = false;
    }
    this.currentBackgroundBlock = backgroundBlock;
    this.currentForgroundBlock = null;
  }


  public changeCursor() {
    if (this.currentForgroundBlock != null || this.currentBackgroundBlock != null) {
      if (this.currentForgroundBlock != null && this.currentForgroundBlock.type === BlockType.REMOVEBLOCK) {
        document.getElementById("puzzleBody").style.cursor = "url('/assets/Resources/img/tiles/remove32x.png'), auto";
      }
      else if (this.currentForgroundBlock != null) {
        document.getElementById("puzzleBody").style.cursor = "url('" + this.currentForgroundBlock.imageSrc + "'), auto";
      }else if(this.currentBackgroundBlock != null){
        document.getElementById("puzzleBody").style.cursor = "url('" + this.currentBackgroundBlock.imageSrc + "'), auto";
      }
    }else {
      document.getElementById("puzzleBody").style.cursor = "auto";
    }
  }

  public changeMultipleTiles(tile) {
    if (this.keyPressed == "Shift") {
      this.changeTile(tile)
    }
  }

  public switchTab(type, tab) {
    switch(type){
      case "block":{
        this.isBlockView = true;
        this.isCodeView = false;
        this.isCostView = false;
        break;
      }
      case "code":{
        this.isBlockView = false;
        this.isCodeView = true;
        this.isCostView = false;
        break;
      }
      case "cost":{
        this.isBlockView = false;
        this.isCodeView = false;
        this.isCostView = true;
        break;
      }
    }

    switch(tab){
      case 1: {
        this.tab1ClassName = "selectedTab";
        this.tab2ClassName = "unSelectedTab";
        this.tab3ClassName = "unSelectedTab";
        break;
      }
      case 2:{
        this.tab1ClassName = "unSelectedTab";
        this.tab2ClassName = "selectedTab";
        this.tab3ClassName = "unSelectedTab";
        break;
      }
      case 3:{
        this.tab1ClassName = "unSelectedTab";
        this.tab2ClassName = "unSelectedTab";
        this.tab3ClassName = "selectedTab";
        break;
      }
    }
  }

  private toggleGridLines() {
    if (this.className == "grid-item") {
      this.className = "grid-item-without-border";
    } else {
      this.className = "grid-item";
    }
  }

  private removeGrid(){
   this.createGrid2DArray();
   this.solution = null;
  }

  private removeTile() {
    this.currentForgroundBlock = this.removeBlock;
    this.currentBackgroundBlock = null;
  }

  doTextareaValueChange(ev) {
      localStorage.setItem("code", JSON.stringify(this.code));

  }

  public toggleTooltip(){
    if(this.toolTipClassName === "notSelected"){
      this.toolTipClassName = "tooltip";
      this.currentBackgroundBlock = null;
      this.currentForgroundBlock = null;
      this.hasTooltip = true;
    }else{
      this.toolTipClassName = "notSelected"
      this.hasTooltip = false;
    }

    if(this.toolTipTextClassName === "notSelectedText"){
      this.toolTipTextClassName = "tooltiptext";
    }else{
      this.toolTipTextClassName = "notSelectedText"
    }
  }


  public selectOrDeselectHero(hero:Hero){
    if(this.selectedHeroList.includes(hero)){
      let index = this.selectedHeroList.indexOf(hero, 0);
      if (index > -1) {
        this.selectedHeroList.splice(index, 1);
      }
    } else {
      if(this.selectedHeroList.length < 3){
        this.selectedHeroList.push(hero);
      }
    }
    localStorage.setItem("selectedHeroes", JSON.stringify(this.selectedHeroList));
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

  public fetchSolution(){
    // this.code = JSON.parse(localStorage.getItem("code"));
  }


}

