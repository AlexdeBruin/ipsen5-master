import {Tile} from "./Tile";
import {Costcard} from "./Costcard";
import {Hero} from "./Hero";

export class Puzzle{

  public levelGrid:Tile[];
  public costCard: Costcard;
  public heroes:Hero[];

  constructor(levelGrid: Tile[], costCard: Costcard) {
    this.levelGrid = levelGrid;
    this.costCard = costCard;
  }
}
