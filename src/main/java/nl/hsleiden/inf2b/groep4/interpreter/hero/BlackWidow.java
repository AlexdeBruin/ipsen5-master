package nl.hsleiden.inf2b.groep4.interpreter.hero;

import nl.hsleiden.inf2b.groep4.puzzle.Tile;

public class BlackWidow extends Hero {

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
		interpreter.substractRunCost("Speciale kracht: Bommen onschadelijk maken", interpreter.getCostCard().getCot_run_specialeAanval());

		Tile tile = interpreter.getTileByPosition(getPosX() + (facingDirection == FacingDirection.RIGHT ? 1 : -1), getPosY());
		if(tile.isSpike() || tile.isBomb()){
			interpreter.log("Bom/spike onschadelijk gemaakt op positie " + (getPosX() + (facingDirection == FacingDirection.RIGHT ? 1 : -1) + " " + getPosY()));
			tile.destroyAllForground();
		} else {
			interpreter.log("Er was geen bom of spike aanwezig!");
		}
	}
}
