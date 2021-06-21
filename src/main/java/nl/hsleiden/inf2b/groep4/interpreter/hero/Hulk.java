package nl.hsleiden.inf2b.groep4.interpreter.hero;

import nl.hsleiden.inf2b.groep4.puzzle.Tile;

public class Hulk extends Hero {

	@Override
	public void flyUp() throws Exception {
		invalidMethod();
	}

	@Override
	public void flyDown() throws Exception {
		invalidMethod();
	}

	@Override
	public void flyForwards() throws Exception {
		invalidMethod();
	}

	@Override
	public void specialAttack() throws Exception {
		interpreter.substractRunCost("Speciale kracht: HULK SMASH", interpreter.getCostCard().getCot_run_specialeAanval());
		Tile tile = interpreter.getTileByPosition(getPosX() + (facingDirection == FacingDirection.RIGHT ? 1 : -1), getPosY());

		boolean succes = tile.destroySolidForground();
		if(succes){
			interpreter.log("Block gesloopt op positie: "+ (tile.getTile_x()) + " " + tile.getTile_y());
		} else {
			interpreter.log("HULK SMASH heeft niks gedaan, want er was geen solid block");
		}
	}
}
