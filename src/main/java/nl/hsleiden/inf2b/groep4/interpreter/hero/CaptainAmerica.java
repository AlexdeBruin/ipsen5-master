package nl.hsleiden.inf2b.groep4.interpreter.hero;

public class CaptainAmerica extends Hero {

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
	public void stepForwards() throws Exception {
		super.stepForwards();
		super.stepForwards();
		interpreter.log("Energie terug gekregen vanwege captain america zijn speciale kracht:" + interpreter.getCostCard().getCost_run_stapVooruit());
		interpreter.getCostCard().addEnergy(interpreter.getCostCard().getCost_run_stapVooruit());
	}

	@Override
	public void stepBackwards() throws Exception {
		super.stepBackwards();
		super.stepBackwards();
		interpreter.log("Energie terug gekregen vanwege captain america zijn speciale kracht:" + interpreter.getCostCard().getCost_run_stapAchteruit());
		interpreter.getCostCard().addEnergy(interpreter.getCostCard().getCost_run_stapAchteruit());
	}

	@Override
	public void jump() throws Exception {
		super.jump();
	}
}
