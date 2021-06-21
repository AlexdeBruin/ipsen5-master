package nl.hsleiden.inf2b.groep4.interpreter.hero;

import nl.hsleiden.inf2b.groep4.puzzle.Tile;

public class Thor extends Hero {

	@Override
	public void specialAttack() throws Exception {

		interpreter.substractRunCost("Speciale kracht: Lighting strike", interpreter.getCostCard().getCot_run_specialeAanval());

		for (int y = getPosY(); y >= 0; y--){
			Tile tile = interpreter.getTileByPosition(getPosX(), y);
				if(tile.destroySolidForground()) {
					interpreter.log("Solid block gesloopt op " + getPosX() + " " + y );
				}
			}
		}
}

