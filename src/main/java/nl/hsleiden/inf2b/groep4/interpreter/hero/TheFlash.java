package nl.hsleiden.inf2b.groep4.interpreter.hero;

import nl.hsleiden.inf2b.groep4.puzzle.Tile;

public class TheFlash extends Hero {

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
		interpreter.substractRunCost("Speciale kracht: FLASH", interpreter.getCostCard().getCot_run_specialeAanval());
		int changeTo = facingDirection == FacingDirection.RIGHT ? 3 : -3;
		Tile tile = interpreter.getTileByPosition(getPosX() + changeTo, getPosY());
		if(tile.isMoveableTile() || tile.isSolidTile()){
			interpreter.log("The flash kon niet teleporteren doordat er een solidblock op de locatie van bestemming stond");
		} else {
			setPosition(tile.getTile_x(), tile.getTile_y());
		}


	}
}
