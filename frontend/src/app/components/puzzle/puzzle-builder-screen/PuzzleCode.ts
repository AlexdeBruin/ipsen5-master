import {Puzzle} from "../models/Puzzle";

export class PuzzleCode {

  public puzzle:Puzzle;
  public code:String;


  constructor(puzzle: Puzzle, code: String) {
    this.puzzle = puzzle;
    this.code = code;
  }
}
