/**
 * Created by VincentSpijkers on 15/05/2018.
 */

export class BackgroundBlock{

  public imageSrc : string;
  public id : number;
  public value : number;


  constructor(src: string, id: number, value: number) {
    this.imageSrc = src;
    this.id = id;
    this.value = value;
  }

}
