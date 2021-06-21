/**
 * Created by VincentSpijkers on 15/05/2018.
 */

import {BlockType} from "./BlockType";
import {TileMap} from "./TileMap";

export class ForgroundBlock{

  public imageSrc : string;
  public id : number;
  public type : BlockType;
  public tileMap : TileMap;


  constructor(imageSrc: string, id: number, type: BlockType) {
    this.imageSrc = imageSrc;
    this.id = id;
    this.type = type;

    // this.hashmap.set("bomTime" , 5);
  }
}
