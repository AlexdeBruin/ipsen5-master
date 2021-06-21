package nl.hsleiden.inf2b.groep4.account;

import javax.persistence.*;

@Entity
@Table(name = "score")
public class Score {

	@Id
	@Column(name = "score_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int scoreId;

	@Column(name = "score_a_solution_ranking")
	private double scoreASolutionRanking;

	@Column(name = "score_a_solution_variation")
	private double scoreASolutionVariation;

	@Column(name = "score_a_solution_relative")
	private double scoreARelativeScore;

	@Column(name = "score_b_solution_score")
	private double scoreBSolutionScore;

	@Column(name = "score_b_solution_energy_left")
	private double scoreBleftEnergy;


	public double getScoreASolutionRanking() {
		return scoreASolutionRanking;
	}

	public void setScoreASolutionRanking(double scoreASolutionRanking) {
		this.scoreASolutionRanking =  Math.round(scoreASolutionRanking * 100.0) / 100.0;
	}

	public double getScoreASolutionVariation() {
		return scoreASolutionVariation;
	}

	public void setScoreASolutionVariation(double scoreASolutionVariation) {
		this.scoreASolutionVariation = Math.round(scoreASolutionVariation * 100.0) / 100.0;
	}

	public double getScoreARelativeScore() {
		return scoreARelativeScore;
	}

	public void setScoreARelativeScore(double scoreARelativeScore) {
		this.scoreARelativeScore = Math.round(scoreARelativeScore * 100.0) / 100.0;
	}

	public double getScoreBSolutionScore() {
		return scoreBSolutionScore;
	}

	public void setScoreBSolutionScore(double scoreBSolutionScore) {
		this.scoreBSolutionScore = Math.round(scoreBSolutionScore * 100.0) / 100.0;
	}

	public double getScoreBleftEnergy() {
		return scoreBleftEnergy;
	}

	public void setScoreBleftEnergy(double scoreBleftEnergy) {
		this.scoreBleftEnergy = Math.round(scoreBleftEnergy * 100.0) / 100.0;
	}

	public Score() {
	}

	public int getScoreId() {
		return scoreId;
	}


}
