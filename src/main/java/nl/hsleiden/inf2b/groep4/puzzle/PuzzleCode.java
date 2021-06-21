package nl.hsleiden.inf2b.groep4.puzzle;

import nl.hsleiden.inf2b.groep4.puzzle.block.Puzzle;

public class PuzzleCode {

	Puzzle puzzle;
	String code;

	public PuzzleCode() {
	}

	public Puzzle getPuzzle() {
		return puzzle;
	}

	public void setPuzzle(Puzzle puzzle) {
		this.puzzle = puzzle;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
