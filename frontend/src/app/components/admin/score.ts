/**
 * Created by VincentSpijkers on 02/06/2018.
 */

export class Score{

  private _scoreId : number;

  public scoreASolutionRanking : number;
  public scoreASolutionVariation : number;
  public scoreARelativeScore : number;
  public scoreBSolutionScore : number;
  public scoreBleftEnergy : number;

  public Score() {
  }


  get scoreId() {
    return this._scoreId;
  }

  get scoreA(): number {
    return this.scoreARelativeScore + this.scoreASolutionRanking + this.scoreASolutionVariation;
  }

  get scoreB(): number {
    return this.scoreBleftEnergy + this.scoreBSolutionScore * 2;
  }


}
