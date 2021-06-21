package nl.hsleiden.inf2b.groep4.interpreter.hero;

import nl.hsleiden.inf2b.groep4.interpreter.hero.Hero;
import nl.hsleiden.inf2b.groep4.puzzle.Coord;

public class DocterStrange extends Hero {

	@Override
	public void flyForwards() throws Exception {
		invalidMethod();
	}

	@Override
	public void specialAttack() throws Exception {
		interpreter.substractRunCost("Speciale kracht: Eye of Agamotto", interpreter.getCostCard().getCot_run_specialeAanval());
		int backTo = heroPositions.size() < 5 ? heroPositions.size() : heroPositions.size() - 5;
		Coord coord = heroPositions.get(backTo);
		setPosition(coord.getX(), coord.getY());
		calculateGravity(true);
	}


}
