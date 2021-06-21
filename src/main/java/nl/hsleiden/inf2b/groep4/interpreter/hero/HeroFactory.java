package nl.hsleiden.inf2b.groep4.interpreter.hero;

public class HeroFactory {


	public Hero getHeroObject(String hero){
		switch (hero){
			case "hero":
				return new Hero();
			case "superman":
				return new Superman();
			case "doctorstrange":
				return new DocterStrange();
			case "ironman":
				return new IronMan();
			case "hulk":
				return new Hulk();
			case "blackwidow":
				return new BlackWidow();
			case "thor":
		 		return new Thor();
			case "captainamerica":
				return new CaptainAmerica();
			case "scarletwitch":
				return new ScarletWitch();
			case "wonderwoman":
				return new WonderWoman();
			case "theflash":
				return new TheFlash();
			case "spiderman":
				//return new Spiderman();
			case "blackpanther":
				return new BlackPanther();
			case "deadpool":
				Hero deadpool = new Deadpool();
				deadpool.addLife();
				return deadpool;
		}
		return null;
	}
}
