/**
 * Created by VincentSpijkers on 02/06/2018.
 */

export class TableScore{

  private _accountName : String;
  private _scoreA : number;
  private _scoreB : number;
  private _totaalScore : number;


  constructor(accountName: String, scoreA: number, scoreB: number) {
    this._accountName = accountName;
    this._scoreA = scoreA;
    this._scoreB = scoreB;
    this._totaalScore = scoreA + scoreB;
  }


  get accountName(): String {
    return this._accountName;
  }

  get scoreA(): number {
    return Math.round(this._scoreA * 100) / 100;
  }

  get scoreB(): number {
    return Math.round(this._scoreB * 100) / 100;
  }

  get totaalScore(): number {
    return Math.round(this._totaalScore * 100) / 100;
  }
}
