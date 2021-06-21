package nl.hsleiden.inf2b.groep4.interpreter.hero;

public class IronMan extends Hero {

	@Override
	public void rotate() throws Exception {
		invalidMethod();
	}

	@Override
	public void jump() throws Exception {
		invalidMethod();
	}

	@Override
	public void specialAttack() throws Exception {
		interpreter.substractRunCost("Speciale kracht: Energy blast", interpreter.getCostCard().getCot_run_specialeAanval());
		if(interpreter.getTileByPosition(posX + 1, posY).isSolidTile()){
			interpreter.log("Het zou onderstandig zijn om van zo dichtbij je energy blast te gebruiken! Je hebt het daarom maar niet gedaan...");
			return;
		}
		for (int i = 2; i < 4; i++) {
			boolean succes = interpreter.getTileByPosition(posX + i, posY).destroySolidForground();
			if(succes){
				interpreter.log("Block gesloopt op positie: "+ ((posX+i) % 25) + " " + posY);
			}
		}
	}


}
