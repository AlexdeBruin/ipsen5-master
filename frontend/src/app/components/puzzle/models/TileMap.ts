import {TileKeyPair} from "./TileKeyPair";

export class TileMap {

   public values:TileKeyPair[] = new Array<TileKeyPair>();


  constructor() {
    this.values = new Array<TileKeyPair>();
  }

  public add(name:string, value:number){
    this.values.push(new TileKeyPair(name, value));
  }

  public update(name:string, value:number){
    this.values.forEach(keypair => {
      if(keypair.name == name){
        keypair.value = value;
      }
    })
  }

}
