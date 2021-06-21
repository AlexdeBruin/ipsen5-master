package nl.hsleiden.inf2b.groep4.interpreter.hero;

import nl.hsleiden.inf2b.groep4.puzzle.Tile;
import nl.hsleiden.inf2b.groep4.puzzle.block.BlockType;
import nl.hsleiden.inf2b.groep4.puzzle.block.ForgroundBlock;

public class ScarletWitch extends Hero {

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

		interpreter.substractRunCost("Speciale kracht: Hexes", interpreter.getCostCard().getCot_run_specialeAanval());
		Tile tile = interpreter.getTileByPosition(getPosX() + (facingDirection == FacingDirection.RIGHT ? 1 : -1), getPosY());
		if(tile.getForgroundBlock() == null){
			interpreter.log("Block op positie " + tile.getTile_x() + " " + tile.getTile_y() + " geplaatst");
			ForgroundBlock forgroundBlock = new ForgroundBlock();
			forgroundBlock.setType(BlockType.SOLIDBLOCK);
			tile.setForgroundBlock(forgroundBlock);
		} else {
			interpreter.log("Kon geen block plaatsen op " + tile.getTile_x() + " " + tile.getTile_y() + " ,want er staat al een object");
		}
	}
}

