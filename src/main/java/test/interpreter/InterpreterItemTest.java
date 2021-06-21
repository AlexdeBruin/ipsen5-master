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

/**
 * Created by VincentSpijkers on 27/06/2018.
 */
public class InterpreterItemTest {

	Puzzle puzzle;
	Interpreter interpreter;
	Solution solution;

	@Before
	public void setup() throws Exception {

		interpreter = null;
		solution = null;

		ObjectMapper mapper = new ObjectMapper();
		puzzle = mapper.readValue(new File("puzzle2.json"), Puzzle.class);

	}

	@Test
	public void testTeleport(){
		String code  = "use deadpool\n" +

				"stapvooruit\n";
		runInterpreter(code);

		assertEquals(solution.isSucces(), false);
		assertEquals(interpreter.getHero().getPosX(), 7);
		assertEquals(interpreter.getHero().getPosY(), 10);
	}

	@Test
	public void testForwardJumpPad(){
		String code  = "use deadpool\n" +
				"\n" +
				"draai \n" +
				"stapvooruit";
		runInterpreter(code);

		assertEquals(solution.isSucces(), false);
		assertEquals(interpreter.getHero().getPosX(), 3);
		assertEquals(interpreter.getHero().getPosY(), 10);
		assertEquals(interpreter.getHero().getFacingDirection(), FacingDirection.LEFT);
	}

	@Test
	public void testBackwardJumpPad(){
		String code  = "use deadpool\n" +
				"stapachteruit";
		runInterpreter(code);

		assertEquals(solution.isSucces(), false);
		assertEquals(interpreter.getHero().getPosX(), 5);
		assertEquals(interpreter.getHero().getPosY(), 10);
		assertEquals(interpreter.getHero().getFacingDirection(), FacingDirection.RIGHT);
	}

	@Test
	public void testAddHeart(){
		String code  = "use deadpool\n" +
				"stapvooruit\n" +
				"stapvooruit";
		runInterpreter(code);

		assertEquals(solution.isSucces(), false);
		assertEquals(interpreter.getHero().getLives(),  3);
	}



	public void runInterpreter(String code){
		solution = new Solution();
		solution.setCode(code);
		solution.setPuzzle(this.puzzle);
		interpreter = new Interpreter(solution);
		interpreter.start();
	}

}
