/**
 * Created by VincentSpijkers on 15/05/2018.
 */

export class Hero {

  public imageSrc : string;
  private id : number;
  public heroName : string;
  public methods : string;
  public specialMove : string;


  constructor(imageSrc: string, id: number, heroName: string, methods: string, specialMove: string) {
    this.imageSrc = imageSrc;
    this.id = id;
    this.heroName = heroName;
    this.methods = methods;
    this.specialMove = specialMove;
  }
}
