package nl.hsleiden.inf2b.groep4.interpreter.hero;

import nl.hsleiden.inf2b.groep4.puzzle.Tile;

public class Spiderman extends Hero {

	private boolean up = true;

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
		interpreter.substractRunCost("Speciale aanval: Loslaten", interpreter.getCostCard().getCot_run_specialeAanval());
		super.calculateGravity(false);
	}

	public boolean isValidStep(int changeX, int changeY) throws Exception{
		Tile tile = interpreter.getTileByPosition(posX + changeX, posY + changeY);

		if(tile.isSolidTile()){
			return false;
		} else if ( tile.isDoor()) {
			boolean isOpened = checkOpenDoor(tile);
			return isOpened;
		}
		return true;
	}

	@Override
	public void calculateGravity(boolean subtractCosts) throws Exception {
		super.calculateGravity(subtractCosts);
	}
}
