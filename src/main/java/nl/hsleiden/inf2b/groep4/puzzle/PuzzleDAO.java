package nl.hsleiden.inf2b.groep4.puzzle;

import com.google.inject.Singleton;
import io.dropwizard.hibernate.AbstractDAO;
import nl.hsleiden.inf2b.groep4.puzzle.block.BackgroundBlock;
import nl.hsleiden.inf2b.groep4.puzzle.block.ForgroundBlock;
import nl.hsleiden.inf2b.groep4.puzzle.block.Hero;
import nl.hsleiden.inf2b.groep4.puzzle.block.Puzzle;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;
import java.util.List;

@Singleton
public class PuzzleDAO extends AbstractDAO<Puzzle> {

	@Inject
	public PuzzleDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}


	public int savePuzzle(Puzzle puzzle){
		Session session = currentSession().getSession();
		session.save(puzzle);
		return puzzle.getId();
	}

	public Puzzle getPuzzle(int id){
		Session session = currentSession().getSession();
		Puzzle puzzle = get(id);
		session.clear();
		return puzzle;
	}


	public List<ForgroundBlock> getAllForgroundBlocks() {
		return list(namedQuery("nl.hsleiden.inf2b.groep4.puzzle.block.ForgroundBlock.findAll"));
	}

	public List<BackgroundBlock> getAllBackgroundBlocks() {
		return list(namedQuery("nl.hsleiden.inf2b.groep4.puzzle.block.BackgroundBlock.findAll"));
	}

	public List<Hero> getAllHeroes(){
		return list(namedQuery("nl.hsleiden.inf2b.groep4.puzzle.block.Hero.findAll"));
	}

	public List<Integer> getAllPuzzleids() {
		List<Integer> id = namedQuery("nl.hsleiden.inf2b.groep4.solution.Solution.getPuzzleids").list();
		currentSession().getSession().clear();
		return id;
	}
}
