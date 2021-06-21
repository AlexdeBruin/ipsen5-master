package nl.hsleiden.inf2b.groep4.interpreter.hero;

public class BlackPanther extends Hero {

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
	public void standOnSpike() throws Exception {
		interpreter.log("Je sneakers zorgen ervoor dat je geen schade krijgt van de spikes");
	}
}
