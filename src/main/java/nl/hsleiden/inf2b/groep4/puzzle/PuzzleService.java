package nl.hsleiden.inf2b.groep4.puzzle;

import com.google.inject.Singleton;
import nl.hsleiden.inf2b.groep4.account.Account;
import nl.hsleiden.inf2b.groep4.account.AccountService;
import nl.hsleiden.inf2b.groep4.interpreter.Interpreter;
import nl.hsleiden.inf2b.groep4.puzzle.block.BackgroundBlock;
import nl.hsleiden.inf2b.groep4.puzzle.block.ForgroundBlock;
import nl.hsleiden.inf2b.groep4.puzzle.block.Hero;
import nl.hsleiden.inf2b.groep4.puzzle.block.Puzzle;
import nl.hsleiden.inf2b.groep4.solution.Solution;
import nl.hsleiden.inf2b.groep4.solution.SolutionService;

import com.google.inject.Inject;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonMap;

@Singleton
public class PuzzleService {

	PuzzleDAO puzzleDAO;
	private final SolutionService solutionService;
	private final AccountService accountService;


	@Inject
	public PuzzleService(PuzzleDAO puzzleDAO, SolutionService solutionService, AccountService accountService) {
		this.puzzleDAO = puzzleDAO;
		this.solutionService = solutionService;
		this.accountService = accountService;
	}

	public List<String> savePuzzle(Puzzle puzzle) {

		List errors = validatePuzzle(puzzle);
		if(errors.size() == 0){
			puzzleDAO.savePuzzle(puzzle);
		}
		return errors;
	}

	public Puzzle getPuzzle(int id) {
		return puzzleDAO.getPuzzle(id);
	}

	public List<ForgroundBlock> getAllForgroundBlocks() {
		return puzzleDAO.getAllForgroundBlocks();
	}

	public List<BackgroundBlock> getAllBackgroundBlocks() {
		return puzzleDAO.getAllBackgroundBlocks();
	}

	public Solution getSolution(int id, String code, Account account) {
		int attempts = solutionService.getAttempts(id, account.getAccountId());
		Solution s = new Solution();
		if (attempts < this.solutionService.getMAXATTEMPTS()) {
			Puzzle puzzle = this.getPuzzle(id);
			Interpreter interpreter = new Interpreter(new Solution(code, puzzle));
			s = interpreter.start();
			s.setAccount(account);
			this.saveSolution(s);
			s.setPuzzle(null); //No sending useless puzzles back
			s.setAccount(null); //No sending account data
		}
		return s;
	}

	public Response getTestSolution(Puzzle puzzle, String code){
		List<String> errors = validatePuzzle(puzzle);
		if(errors.size() == 0) {
			Interpreter interpreter = new Interpreter(new Solution(code, puzzle));
			Solution solution = interpreter.start();
			return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(singletonMap("solution", solution)).build();
		}
		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(singletonMap("errors", errors)).build();
	}

	public void saveSolution(Solution solution) {
		solutionService.saveSolution(solution);
	}

	public Solution getBestSolution(int id, Account accountModel) {
		return solutionService.getBestSolution(id, accountModel);
	}

	public int getAttempts(int id, Account accountModel) {
		return solutionService.getMAXATTEMPTS() - solutionService.getAttempts(id, accountModel.getAccountId());
	}

	private ArrayList<Solution> getDoneList(int accountid) {
		return solutionService.getDoneList(accountid);
	}

	private ArrayList<Solution> getTriedList(int accountid) {
		return solutionService.getTriedList(accountid);
	}

	public ArrayList<Solution> getdoneAttemptsLeft(int accountid) {
		return solutionService.getdoneAttemptsLeft(accountid);
	}

	public ArrayList<Solution> getFailList(int accountid) {
		return solutionService.getFailList(accountid);
	}

	private ArrayList<Account> getAllAccounts() {
		return this.accountService.getAccountNamePuzzleID();
	}

	public List<String> validatePuzzle(Puzzle puzzle) {
		List<String> errors = new ArrayList<>();
		boolean startTileFound = false;
		boolean villianTileFound = false;

		if (puzzle.getHeroes().size() < 3) {
			errors.add("Je moet 3 heroes selecteren, je hebt er maar " + puzzle.getHeroes().size());
		}

		for (Tile tile : puzzle.getLevelGrid()) {
			ArrayList<String> tileErrors = new ArrayList<>();
			if(tile.getForgroundBlock() == null){
				continue;
			}
			switch (tile.getForgroundBlock().getType()) {
				case SOLIDBLOCK:
				case SPIKE:
				case ITEM_EXTRALIFE:
				case INSTAKILLBLOCK:
				case MOVEABLEBLOCK:
					//make sure the tilemap is set to null so no useless data is put in the database
					tile.setTileMap(null);
					break;
				case BOMB:
					tileErrors.add(checkTileMap(tile, "rondes", 20 , "bom"));
					break;
				case DOOR:
					tileErrors.add(checkTileMap(tile, "id", 99, "deur"));
					break;
				case ITEM_KEY:
					tileErrors.add(checkTileMap(tile, "id", 99, "sleutel"));
					break;
				case TELEPORT:
					tileErrors.add(checkTileMap(tile, "x", 99, "teleport"));
					tileErrors.add(checkTileMap(tile, "y", 99, "teleport"));
					break;
				case VILLIANBLOCK:
					if(villianTileFound) {
						tileErrors.add("Er mag niet meer dan 1 villian tile aanwezig zijn");
					} else {
						villianTileFound = true;
					}
					break;
				case HEROBLOCK:
					if(startTileFound) {
						tileErrors.add("Er mag niet meer dan 1 start tile aanwezig zijn");
					} else {
						startTileFound = true;
					}
					break;
				case JUMPINGPAD:
					tileErrors.add(checkTileMap(tile, "omhoog", 19, "jumping pad"));
					break;
				case ITEM_ENERGYTANK:
					tileErrors.add(checkTileMap(tile, "energie", 10000, "energie tank"));
					break;
				case SPEEDBOOST:
					tileErrors.add(checkTileMap(tile, "vooruit", 24, "speed boost"));
					break;
			}
			for (String error: tileErrors) {
				if(error != null)
				errors.add(error);
			}
		}
		return errors;
	}

	private String checkTileMap(Tile tile, String value, int maxValue,  String object){
		if (tile.getTileMap().getValueByKey(value) == -1) {
			return ("De " + object + " op tile " + tile.getTile_x() + " " + tile.getTile_y() + " is ongeldig, plaats opnieuw");
		} else if (tile.getTileMap().getValueByKey(value) < 0 || tile.getTileMap().getValueByKey(value) > maxValue) {
			return ("De " + object + " op tile " + tile.getTile_x() + " " + tile.getTile_y() + " heeft een ongeldige waarde '" + value + "'" +
					" letop: deze waarde moet minimaal 0 zijn en maximaal " + maxValue);
		}
		return null;
	}

	public PuzzleListModel getPuzzleList(int accountid) {
		PuzzleListModel puzzleList = new PuzzleListModel();
		ArrayList<Account> todoModels = getAllAccounts();
		Account authAccount = null;
		for(Account a : todoModels) {
			if(a.getAccountId() == accountid) {
				authAccount = a;
			}
		}
		todoModels.remove(authAccount);
		ArrayList<Account> triedModels = splitList(todoModels, getTriedList(accountid));
		ArrayList<Account> doneModels = splitList(todoModels, getDoneList(accountid));
		ArrayList<Account> failModels = splitList(todoModels, getFailList(accountid));
		ArrayList<Account> doneAttemptsLeftModels = splitList(todoModels, getdoneAttemptsLeft(accountid));

		todoModels.removeAll(triedModels);
		todoModels.removeAll(doneModels);
		todoModels.removeAll(failModels);
		todoModels.removeAll(doneAttemptsLeftModels);

		failModels.removeAll(doneAttemptsLeftModels);

		triedModels.removeAll(doneModels);

		puzzleList.setTodo(todoModels);
		puzzleList.setTried(triedModels);
		puzzleList.setDone(doneModels);
		puzzleList.setFail(failModels);
		puzzleList.setNoDoneAttemptsLeft(doneAttemptsLeftModels);
		return puzzleList;
	}

	private ArrayList<Account> splitList(ArrayList<Account> todoModels, ArrayList<Solution> solutions) {
		ArrayList<Integer> puzzleIds = new ArrayList<>();
		ArrayList<Account> splitList = new ArrayList<>();
		for(Solution s : solutions) {
			puzzleIds.add(s.getPuzzle().getId());
		}
		for(Account a : todoModels) {
			for(Integer i : puzzleIds) {
				if(a.getPuzzle().getId() == i) {
					splitList.add(a);
				}
			}
		}
		todoModels.remove(splitList);
		return splitList;
	}

	public List<Hero> getAllHeroes(){return puzzleDAO.getAllHeroes();}

	public List<Integer> getSandboxList() {
		return puzzleDAO.getAllPuzzleids();
	}

	public Solution getSandboxSolution(int puzzleid, String code) {
		Solution s = new Solution();
		Puzzle puzzle = this.getPuzzle(puzzleid);
		Interpreter interpreter = new Interpreter(new Solution(code, puzzle));
		s = interpreter.start();
		s.setPuzzle(null); //No sending useless puzzles back
		return s;
	}
}
