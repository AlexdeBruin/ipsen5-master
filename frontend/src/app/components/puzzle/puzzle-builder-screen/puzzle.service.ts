import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from "@angular/common/http";
import {Puzzle} from "../models/Puzzle";
import {ForgroundBlock} from "../models/ForgroundBlock";
import {BackgroundBlock} from "../models/BackgroundBlock";
import {Hero} from "../models/Hero";
import {Costcard} from "../models/Costcard";
import {error} from "util";
import { Solution } from '../models/Solution';
import { PuzzleListModel } from '../models/PuzzleListModel';
import {PuzzleCode} from "./PuzzleCode";
import {Observable} from "rxjs/Rx";

@Injectable({
  providedIn: 'root'
})
export class PuzzleService {


  public costcard : Costcard = new Costcard();

  constructor(public http : HttpClient) {
    this.getCostCard();
  }

  public savePuzzle(puzzle:PuzzleCode){
    return this.http.post("api/puzzle/create", puzzle)
  }

  public saveSandboxPuzzle(puzzel : PuzzleCode) {
    return this.http.post("api/puzzle/create/sandbox", puzzel)
  }

  public getAllForgroundBlocks(){
    return this.http.get<ForgroundBlock[]>("api/puzzle/allForgroundBlocks")
  }

  public getAllBackgroundBlocks(){
    return this.http.get<BackgroundBlock[]>("api/puzzle/allBackgroundBlocks")
  }

  public getAllHeroes(){
    return this.http.get<Hero[]>("api/puzzle/allHeroes")
  }

  public getSolution(id, code) {
    return this.http.post<Solution>("api/puzzle/" + id + "/solution", code)
  }

  public getSandboxSolution(id, code) {
    return this.http.post<Solution>("api/puzzle/" + id + "/sandbox", code)
  }

  public getTestSolution(puzzleCode:PuzzleCode){
    return this.http.post("api/puzzle/test", puzzleCode);
  }

  public getMySolution(){
    return this.http.get("api/puzzle/mysolution");
  }
  public getCostCard() {
    if (this.costcard == null || this.costcard == undefined) {
      this.costcard = new Costcard();
    }
    return this.costcard;
  }

  public saveCostCard() {
    return this.http.post("api/CostCard/saveCostCard", this.costcard).subscribe(
      data =>  {
        alert("De kosten kaart is succesvol opgeslagen")
      },
      error => {
      alert ("De kosten kaart kon niet opgeslagen worden. Zorg dat je eerst de puzzel opslaat en dat alle velden zijn ingevoerd!")
      }
    )

  }

  public getPuzzle(id : Number) {
    return this.http.get<Puzzle>("api/puzzle/" + id)
  }

  public getBestSolution(id) {
    return this.http.get<Solution>("api/puzzle/" + id + "/bestSolution")
  }

  public getAttempts(id) {
    return this.http.get<number>("api/puzzle/" + id + "/attempts")
  }

  public getPuzzleList() {
    return this.http.get<PuzzleListModel>("api/puzzle/puzzleList")
  }

  public getSandboxList() {
    return this.http.get<Number[]>("api/puzzle/sandboxList")
  }

}
