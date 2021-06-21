package nl.hsleiden.inf2b.groep4.interpreter.hero;

import nl.hsleiden.inf2b.groep4.puzzle.Tile;

public class Superman extends Hero {

	@Override
	public void stepForwards() throws Exception {
		invalidMethod();
	}

	@Override
	public void stepBackwards() throws Exception {
		invalidMethod();
	}

	@Override
	public void jump() throws Exception {
		invalidMethod();
	}

	@Override
	public void specialAttack() throws Exception {
		interpreter.substractRunCost("Speciale kracht: Superhero landing", interpreter.getCostCard().getCot_run_specialeAanval());

		while(posY < 19 && interpreter.getTileByPosition(posX, posY+1) != null){
			Tile tile = interpreter.getTileByPosition(posX, posY +1);
			if(tile.isSolidTile()){
				return;
			}
			changePosition(0, 1);
		}


	}
}

