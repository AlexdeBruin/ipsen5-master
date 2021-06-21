package nl.hsleiden.inf2b.groep4.interpreter.hero;

import nl.hsleiden.inf2b.groep4.interpreter.Interpreter;
import nl.hsleiden.inf2b.groep4.interpreter.InterpreterException;
import nl.hsleiden.inf2b.groep4.puzzle.Coord;
import nl.hsleiden.inf2b.groep4.puzzle.Tile;
import nl.hsleiden.inf2b.groep4.puzzle.block.BlockType;

import java.util.ArrayList;


public class Hero {

	int posX;
	int posY;
	protected FacingDirection facingDirection;
	protected Interpreter interpreter;
	protected ArrayList<Coord> heroPositions = new ArrayList<>();
	int lives = 1;

	public Hero(){
		this.facingDirection = FacingDirection.RIGHT;
	}


	public void doAction(String action) throws Exception {

		switch(action){

			case "draai":
				rotate();
				break;
			case "stapvooruit":
				stepForwards();
				break;
			case "stapachteruit":
				stepBackwards();
				break;
			case "vliegomhoog":
				flyUp();
				break;
			case "vliegvooruit":
				flyForwards();
				break;
			case "vliegomlaag":
				flyDown();
				break;
			case "specialekracht":
				specialAttack();
				break;
			case "spring":
				jump();
				break;
			default:
				interpreter.end("niet bestaande methode");
				break;
		}
		interpreter.getTileActions().tickRound();
	}

	public void rotate() throws Exception{
		interpreter.substractRunCost("draaien", interpreter.getCostCard().getCost_run_draai());
		if(facingDirection == FacingDirection.LEFT){
			facingDirection = FacingDirection.RIGHT;
		} else {
			facingDirection = FacingDirection.LEFT;
		}
	}

	public void stepForwards() throws Exception {

		if(isValidStep(facingDirection == FacingDirection.RIGHT ? 1 : -1, 0)) {
			changePosition(facingDirection == FacingDirection.RIGHT ? 1 : -1, 0);
			interpreter.substractRunCost("vooruit lopen", interpreter.getCostCard().getCost_run_stapVooruit());
			calculateGravity(true);
		}
	}

	public void stepBackwards() throws Exception {

		if (isValidStep(facingDirection == FacingDirection.RIGHT ? -1 : 1, 0)) {
			changePosition(facingDirection == FacingDirection.RIGHT ? -1 : 1, 0);
			interpreter.substractRunCost("achterluit lopen", interpreter.getCostCard().getCost_run_stapAchteruit());
			calculateGravity(true);
		}
	}

	public void flyUp() throws Exception {

		if(isValidStep(0, -1)) {
			changePosition(0, -1);
			interpreter.substractRunCost("omhoog vliegen", interpreter.getCostCard().getCost_run_vliegOmhoog());
		}

	}

	public void flyDown() throws Exception {

		if(isValidStep(0, 1)) {
			changePosition(0, 1);
			interpreter.substractRunCost("omlaag vliegen", interpreter.getCostCard().getCost_run_vliegOmlaag());
		}

	}

	public void flyForwards() throws Exception{

		if(isValidStep(facingDirection == FacingDirection.RIGHT ? 1 : -1, 0)) {
			changePosition(facingDirection == FacingDirection.RIGHT ? 1 : -1, 0);
			interpreter.substractRunCost("vooruit vliegen", interpreter.getCostCard().getCost_run_vliegVooruit());
		}

	}

	public void jump() throws Exception {

		if(isValidStep(1, -1)) {
			changePosition(1, -1);
			interpreter.substractRunCost("springen", interpreter.getCostCard().getCost_run_jump());
			calculateGravity(true);
		}
	}


	public void specialAttack() throws Exception {
		invalidMethod();
	}

	public boolean isValidStep(int changeX, int changeY) throws Exception{
		Tile tile = interpreter.getTileByPosition(posX + changeX, posY + changeY);

		if(tile == null || tile.isSolidTile()){
			bots();
			return false;
		} else if ( tile.isDoor()) {
			boolean isOpened = checkOpenDoor(tile);
			if (!isOpened) {
				bots();
			}
			return isOpened;
		}
		return true;
	}

	public void bots() throws Exception{
		interpreter.log("AU... gebotst");
		interpreter.substractRunCost("botsen op (" + posX + "," + posY + ").", interpreter.getCostCard().getCost_punishment_botsen());
	}

	public boolean checkOpenDoor(Tile tile) throws Exception{
		if(!tile.isDoor()){
			return false;
		}
		boolean isopen =  interpreter.getTileActions().openDoor(tile);
		if(isopen) {
			interpreter.log("Deur geopent op " + "(" + posX + "," + posY + ")");
		}
		return isopen;
	}

	public void calculateGravity(boolean subtractCosts) throws Exception{

		while(posY < 19 && interpreter.getTileByPosition(posX, posY+1) != null){
			Tile tile = interpreter.getTileByPosition(posX, posY +1);
			if(tile.isDoor()) {
				if(!checkOpenDoor(tile)){
					return;
				}
			}
			if(tile.isSolidTile()){
				return;
			}
			changePosition(0, 1);
			if(subtractCosts) {
				interpreter.substractRunCost("gevallen naar (" + posX + "," + posY + ").", interpreter.getCostCard().getCost_punishment_vallen());
			}
		}

	}

	public boolean changePosition(int changeX, int changeY) throws Exception{
		//interpreter.getTileActions().handleMoveable(getPosX(), getPosY(), changeX, changeY);
		return setPosition(posX + changeX, posY + changeY);
	}

	public boolean setPosition(int x, int y) throws Exception {

		if(x >= 25){
			x -= 25;
		}
		if(x < 0){
			x += 25;
		}

		posX = x;
		posY = y;
		interpreter.log("Verplaatst naar " + posX +  " " + posY );
		heroPositions.add(new Coord(posX, posY));

		boolean hasNewForce = interpreter.getTileActions().exectueTileAction(interpreter.getTileByPosition(x, y));

		if(interpreter.isHeroAtEndPosition()){
			throw new InterpreterException("Einde gevonden!");
		}

		return hasNewForce;
	}

	public void handleJumpingPad(int amountUp) throws Exception{
			int changeX = facingDirection == FacingDirection.RIGHT ? 1 : - 1;

		for (int i = 0; i < amountUp; i++) {
			if (isValidStep(0, -1)) {
				boolean hasNewForce = changePosition(0, -1);

				if(hasNewForce){
					//Hero found a tile that has gives the hero a new force, this means that the current force will be
					//canceled or weird stuff will happen.
					return;
				}
			} else {
				return;
			}
		}
		if (isValidStep(changeX, 0)) {
			changePosition(changeX, 0);
		}
	}

	public void handleSpeedboost(int amountForward) throws Exception {
		int changeX = facingDirection == FacingDirection.RIGHT ? 1 : -1;

		for (int i = 0; i < amountForward; i++) {
			if (isValidStep(0, 0)) {
				boolean hasNewForce = changePosition(changeX,0 );
				if(hasNewForce){
					//Hero found a tile that has gives the hero a new force, this means that the current force will be
					//canceled or weird stuff will happen.
					return;
				}
			} else {
				return;
			}
		}
	}

	public void standOnSpike() throws Exception{
		removeLife();
	}
	public void setInterpreter(Interpreter interpreter) {
		this.interpreter = interpreter;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public void invalidMethod() throws Exception {
		interpreter.end("Ongeldige actie voor deze hero");
	}

	public void addLife(){
		lives++;
	}
	public void removeLife() throws Exception{
		lives--;
			interpreter.log("-1 leven nog " + lives + " over");
		if(lives <= 0){
			interpreter.end("geen levens meer over");
		}
	}

	public int getLives(){
		return this.lives;
	}

	public ArrayList<Coord> getHeroPositions() {
		return heroPositions;
	}
	public FacingDirection getFacingDirection() {
		return facingDirection;
	}
}
