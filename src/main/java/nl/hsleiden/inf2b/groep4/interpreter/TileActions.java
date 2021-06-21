package nl.hsleiden.inf2b.groep4.interpreter;

import nl.hsleiden.inf2b.groep4.puzzle.Tile;
import nl.hsleiden.inf2b.groep4.puzzle.block.BlockType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TileActions {

	private Interpreter interpreter;
	private ArrayList<Tile> bombTiles = new ArrayList<>();
	private Map<Tile, Boolean> spikeTiles = new HashMap<>();

	public TileActions(Interpreter interpreter) {

		this.interpreter = interpreter;
		activateAllSpikes();
	}


	public boolean exectueTileAction(Tile tile) throws Exception{

		if(tile.getForgroundBlock() == null){
			return false;
		}
		BlockType type = tile.getForgroundBlock().getType();

		if(pickupItem(tile)){
			//the tile had a pickupable so we dont need to continue running.
			return false;
		}
		switch (type){
			case SOLIDBLOCK:
				break;
			case INSTAKILLBLOCK:
				interpreter.end("Dood gegaan door een lava of water block");
				break;
			case MOVEABLEBLOCK:
				break;
			case DOOR:
				break;
			case BOMB:
				executeBomb(tile);
				break;
			case TELEPORT:
				goThroughportal(tile);
				break;
			case JUMPINGPAD:
				standOnJumpingpad(tile);
				return true;
			case SPEEDBOOST:
				standOnSpeedBoost(tile);
				return true;
			case SPIKE:
				checkSpikes(tile);

		}
		return false;

	}

	public void activateAllSpikes() {
		for (Tile tile:interpreter.getSolution().getPuzzle().getLevelGrid()) {
			if(tile.isSpike()){
				spikeTiles.put(tile, true);
			}
		}
	}
	public void tickRound() throws Exception{
		ArrayList<Tile> bombTilesLocal = new ArrayList<>(bombTiles);

		for (Tile tile:bombTilesLocal) {
			tile.getTileMap().updateValue("rondes", tile.getTileMap().getValueByKey("rondes") - 1);
			if(tile.getTileMap().getValueByKey("rondes") <= 0) {
				explodeBomb(tile);
				tile.destroyAllForground();
				bombTiles.remove(tile);
			}
		}

		//lower/raise all the spikes
		for (Tile tile : spikeTiles.keySet()) {
			spikeTiles.put(tile, !spikeTiles.get(tile));
		}
	}

	private void executeBomb(Tile tile) throws Exception{
		int roundsToExplosion = tile.getTileMap().getValueByKey("rondes");
		if(roundsToExplosion <= 0){
			explodeBomb(tile);
			tile.destroyAllForground();
			bombTiles.remove(tile);
		} else {
			bombTiles.add(tile);
		}
	}

	private void explodeBomb(Tile tile) throws Exception {
		interpreter.log("BOEEEEEM bom ontploft op " + tile.getTile_x() + " " + tile.getTile_y() + ".");

		if(tile.getTile_x() == interpreter.getHero().getPosX() && tile.getTile_y() == interpreter.getHero().getPosY()){

			interpreter.getHero().removeLife();
		}
	}

	public boolean pickupItem(Tile tile) throws Exception{
		if(tile.getForgroundBlock() == null){
			return false;
		}
		BlockType type = tile.getForgroundBlock().getType();
		switch (type) {
			case ITEM_KEY:
				addKey(tile);
				break;
			case ITEM_ENERGYTANK:
				addEnergy(tile);
				break;
			case ITEM_EXTRALIFE:
				addLife(tile);
				break;
			default:
				return false;
		}
		return true;
	}

	private void addKey(Tile tile) throws Exception {

		int keyValue = tile.getTileMap().getValueByKey("id");
		interpreter.addKey(keyValue);
		interpreter.log("Sleutel met id " + keyValue + " gevonden!");
		tile.destroyAllForground();

	}

	private void addEnergy(Tile tile) {

		int energyamt = tile.getTileMap().getValueByKey("energie");
		interpreter.getCostCard().addEnergy(energyamt);
		interpreter.log("Energie Tank gevonden! " + energyamt + " energie erbij");
		tile.destroyAllForground();

	}

	private void addLife(Tile tile){
		interpreter.getHero().addLife();
		interpreter.log("Extra leven gevonden!");
		tile.destroyAllForground();
	}

	public boolean openDoor(Tile tile){

		int doorId = tile.getTileMap().getValueByKey("id");
		if(interpreter.getKeys().contains(doorId)){
			tile.destroyAllForground();
			return true;
		} else {
			return false;
		}
	}

	public void goThroughportal(Tile tile) throws Exception{
		interpreter.log("Je hebt een portaal gevonden");
		int x = tile.getTileMap().getValueByKey("x");
		int y = tile.getTileMap().getValueByKey("y");

		Tile destination = interpreter.getTileByPosition(x, y);
		if(destination.isPortal()){
			interpreter.log("Het klinkt niet verstandig om door een portal te gaan die naar een andere portal gaat");
		} else if(destination.isDoor()){
			if(!openDoor(destination)){
				interpreter.getHero().bots();
			}
		} else if(tile.isSolidTile() || tile.isMoveableTile()){
			interpreter.getHero().bots();
		} else {
			interpreter.log("Je stapt door het portaal...");
			interpreter.getHero().setPosition(x, y);
//			interpreter.getHero().calculateGravity(true);
		}
	}

	public void standOnJumpingpad(Tile tile) throws Exception {
		int amountUp = tile.getTileMap().getValueByKey("omhoog"); //FIXME
			interpreter.log("Je stapt op een jumping pad");
			interpreter.getHero().handleJumpingPad(amountUp);
	}

	public void standOnSpeedBoost(Tile tile) throws Exception {
		int x = tile.getTileMap().getValueByKey("vooruit");
		interpreter.log("Je stapt op een speedboost");
		interpreter.getHero().handleSpeedboost(x); //FIXME
	}

	public void checkSpikes(Tile tile) throws Exception {
		boolean isup = spikeTiles.get(tile);
		if(isup){
			interpreter.log("Geraakt door een spike");
			interpreter.getHero().standOnSpike();
		}
	}


//	public void handleMoveable(int startX, int startY, int changeX, int changeY){
//		Tile tile = interpreter.getTileByPosition(startX + changeX, startY + changeY);
//
//		if(!tile.isMoveableTile()){
//			return;
//		}
//
//		Tile nextTile = interpreter.getTileByPosition(tile.getTile_x() + changeX, tile.getTile_y() + changeY);
//
//		if (nextTile.isMoveableTile() || nextTile.getForgroundBlock() == null){
//			handleMoveable(tile.getTile_x(), tile.getTile_y(), changeX, changeY);
//			interpreter.log("block verplaatst van " + tile.getTile_x() + " " + tile.getTile_y() + " naar " + nextTile.getTile_x() + " " + nextTile.getTile_y());
//			nextTile.setForgroundBlock(tile.getForgroundBlock());
//			tile.setForgroundBlock(null);
//		}
//
//
//
//	}
}
