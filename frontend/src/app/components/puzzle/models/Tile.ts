import {BackgroundBlock} from "./BackgroundBlock";
import {ForgroundBlock} from "./ForgroundBlock";
import {TileMap} from "./TileMap";
/**
 * Created by VincentSpijkers on 03/05/2018.
 */

export class Tile{
  public tile_x : number;
  public tile_y : number;
  public forgroundBlock : ForgroundBlock;
  public backgroundBlock : BackgroundBlock;
  public tileMap: TileMap;


  constructor(tile_x: number, tile_y: number, forgroundBlock: ForgroundBlock, backgroundBlock: BackgroundBlock) {
    this.tile_x = tile_x;
    this.tile_y = tile_y;
    this.forgroundBlock = forgroundBlock;
    this.backgroundBlock = backgroundBlock;
  }


}
