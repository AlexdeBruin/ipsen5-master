package test.interpreter;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hsleiden.inf2b.groep4.interpreter.Interpreter;
import nl.hsleiden.inf2b.groep4.interpreter.hero.FacingDirection;
import nl.hsleiden.inf2b.groep4.puzzle.block.Puzzle;
import nl.hsleiden.inf2b.groep4.solution.Solution;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InterpreterTest {


	Puzzle puzzle;
	Interpreter interpreter;
	Solution solution;

	@Before
	public void setup() throws Exception {

		interpreter = null;
		 solution = null;

		ObjectMapper mapper = new ObjectMapper();
		puzzle = mapper.readValue(new File("puzzle.json"), Puzzle.class);

	}

	@Test
	public void testNoHeroSelected() {

		String code = "stapvooruit";
		runInterpreter(code);

		assertEquals(solution.isSucces(), false);
		assertEquals(interpreter.getHero(), null);


	}

	@Test
	public void testHeroSelected() {

		String code = "use ironman";
		runInterpreter(code);

		assertNotNull(interpreter.getHero());


	}

	@Test
	public void testStapvooruit() {

		String code = "use ironman\n" +
				"stapvooruit";
		runInterpreter(code);

		assertEquals(solution.isSucces(), false);
		assertEquals(interpreter.getHero().getPosX(), 5);
		assertEquals(interpreter.getHero().getPosY(), 9);

	}

	@Test
	public void testStapAchteruit() {

		String code = "use hulk\n" +
				"stapachteruit";
		runInterpreter(code);

		assertEquals(solution.isSucces(), false);
		assertEquals(interpreter.getHero().getPosX(), 3);
		assertEquals(interpreter.getHero().getPosY(), 9);

	}

	@Test
	public void testVliegOmhoog() {

		String code = "use ironman\n" +
				"vliegomhoog";
		runInterpreter(code);

		assertEquals(solution.isSucces(), false);
		assertEquals(interpreter.getHero().getPosX(), 4);
		assertEquals(interpreter.getHero().getPosY(), 8);

	}

	@Test
	public void testVliegOmlaag() {

		String code = "use ironman\n" +
				"vliegomhoog\n" +
				"vliegomhoog\n" +
				"vliegomlaag";
		runInterpreter(code);

		assertEquals(solution.isSucces(), false);
		assertEquals(interpreter.getHero().getPosX(), 4);
		assertEquals(interpreter.getHero().getPosY(), 8);

	}

	@Test
	public void testSpring() {

		String code = "use hulk\n" +
				"spring";
		runInterpreter(code);

		assertEquals(solution.isSucces(), false);
		assertEquals(interpreter.getHero().getPosX(), 5);
		assertEquals(interpreter.getHero().getPosY(), 9);
		assertEquals(interpreter.getHero().getHeroPositions().size(),  3);
		//also checks gravity
	}

	@Test
	public void testIfStatementTrue() {

		String code = "use hulk\n" +
				"if 1 == 1 {\n" +
				"stapvooruit\n" +
				"}";
		runInterpreter(code);

		assertEquals(solution.isSucces(), false);
		assertEquals(interpreter.getHero().getPosX(), 5);
		assertEquals(interpreter.getHero().getPosY(), 9);
	}

	@Test
	public void testIfStatementFalse() {

		String code = "use hulk\n" +
				"if 2 == 1 {" +
				"stapvooruit" +
				"}";
		runInterpreter(code);

		assertEquals(solution.isSucces(), false);
		assertEquals(interpreter.getHero().getPosX(), 4);
		assertEquals(interpreter.getHero().getPosY(), 9);
	}

	@Test
	public void testWhileStatementTrue() {

		String code = "use hulk\n" +
				"a = 0\n" +
				"\n" +
				"while a < 5 {\n" +
				"a += 1\n" +
				"stapvooruit\n" +
				"}";
		runInterpreter(code);

		assertEquals(solution.isSucces(), false);
		assertEquals(interpreter.getHero().getPosX(), 9);
		assertEquals(interpreter.getHero().getPosY(), 9);
	}
	@Test
	public void testWhileStatementInfinite(){

		String code = "use hulk\n" +
				"while 1 == 1 {\n" +
				"stapvooruit\n" +
				"}";
		runInterpreter(code);

		assertEquals(solution.isSucces(), true);
		assertEquals(interpreter.getHero().getPosX(), 18);
		assertEquals(interpreter.getHero().getPosY(), 9);
	}

	@Test
	public void testWhileStatementFalse(){

		String code = "use hulk\n" +
				"while 1 != 1 {\n" +
				"stapvooruit\n" +
				"}";
		runInterpreter(code);

		assertEquals(solution.isSucces(), false);
		assertEquals(interpreter.getHero().getPosX(), 4);
		assertEquals(interpreter.getHero().getPosY(), 9);
	}

	@Test
	public void testKleuroog(){

		String code = "use hulk\n" +
				"import kleuroog\n" +
				"if kleuroog == 1 {\n" +
				"stapvooruit\n" +
				"}";
		runInterpreter(code);

		assertEquals(solution.isSucces(), false);
		assertEquals(interpreter.getHero().getPosX(), 5);
		assertEquals(interpreter.getHero().getPosY(), 9);
	}

	@Test
	public void testDraai(){

		String code = "use hulk\n" +
				"draai";
		runInterpreter(code);

		assertEquals(solution.isSucces(), false);
		assertEquals(interpreter.getHero().getFacingDirection(), FacingDirection.LEFT);
	}

	@Test
	public void TestToOtherSideMap(){

		String code = "use hulk\n" +
				"stapachteruit\n" +
				"stapachteruit\n" +
				"stapachteruit\n" +
				"stapachteruit\n" +
				"stapachteruit\n";
		runInterpreter(code);

		assertEquals(solution.isSucces(), false);
		assertEquals(interpreter.getHero().getPosX(), 24);
		assertEquals(interpreter.getHero().getPosY(), 9);
	}

	@Test
	public void testSolidBlockUnMoveable(){

		String code = "use ironman\n" +
				"vliegomlaag";
		runInterpreter(code);

		assertEquals(solution.isSucces(), false);
		assertEquals(interpreter.getHero().getPosX(), 4);
		assertEquals(interpreter.getHero().getPosY(), 9);
	}



	public void runInterpreter(String code){
		solution = new Solution();
		solution.setCode(code);
		solution.setPuzzle(this.puzzle);
		interpreter = new Interpreter(solution);
		interpreter.start();
	}

}
