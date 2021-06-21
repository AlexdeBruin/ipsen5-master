package nl.hsleiden.inf2b.groep4.interpreter.hero;

import nl.hsleiden.inf2b.groep4.puzzle.Tile;

public class WonderWoman extends Hero {


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
		interpreter.substractRunCost("Speciale kracht: Zweep", interpreter.getCostCard().getCot_run_specialeAanval());

		if(facingDirection == FacingDirection.RIGHT){
			for (int x = getPosX() + 1; x < getPosX() + 5 ; x++) {
				if(!getItemFromTile(interpreter.getTileByPosition(x, getPosY()))){
					interpreter.log("Zweep heeft iets geraakt op tile " + x + " " + getPosY() + " en kon daardoor niet verder");
					return;
				}
			}
		} else {
			for (int x = getPosX() - 1; x > getPosX() - 5 ; x--) {
				if(!getItemFromTile(interpreter.getTileByPosition(x, getPosY()))){
					interpreter.log("Zweep heeft iets geraakt op tile " + x + " " + getPosY() + " en kon daardoor niet verder");
					return;
				}
			}
		}

	}

	/**
	 *
	 * @param tile
	 * @return false if it hits something thats not an item or a null background
	 * @throws Exception
	 */
	private boolean getItemFromTile(Tile tile) throws Exception {
		if(tile.isMoveableTile() || tile.isDoor() || tile.isSolidTile()){
			return false;
		}
		interpreter.getTileActions().pickupItem(tile);
		return true;
	}
}
