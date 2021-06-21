package nl.hsleiden.inf2b.groep4.solution;

import com.google.inject.Singleton;
import nl.hsleiden.inf2b.groep4.account.Account;

import com.google.inject.Inject;
import nl.hsleiden.inf2b.groep4.account.AccountDAO;
import nl.hsleiden.inf2b.groep4.account.AccountService;
import nl.hsleiden.inf2b.groep4.account.Score;
import nl.hsleiden.inf2b.groep4.puzzle.PuzzleDAO;
import nl.hsleiden.inf2b.groep4.puzzle.block.Puzzle;

import javax.validation.constraints.Max;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Singleton
public class SolutionService {

    private int MAXATTEMPTS;
    private final SolutionDAO solutionDAO;
	private AccountService accountService;
	private PuzzleDAO puzzleDAO;

    @Inject
    public SolutionService(SolutionDAO solutionDAO, AccountService accountService, PuzzleDAO puzzleDAO) {
        this.solutionDAO = solutionDAO;
        this.accountService = accountService;
        this.puzzleDAO = puzzleDAO;
        MAXATTEMPTS = this.solutionDAO.getMAXATTEMPTS();
    }


    public void updateScore(Solution solution){
		updateScoreForMaker(solution.getPuzzle(), solution);
		calculateBscore(solution.getAccount(), solution);
		accountService.updateAccountForScores(solution.getAccount(), solution.getPuzzle().getAccount());
	}

	public void isBestSolution(Solution checkSolution,  List<Solution> currentBestSolution){
    	boolean foundSolution = false;

		for (Solution solution: currentBestSolution) {
			if(solution.getAccount().getAccountId() == checkSolution.getAccount().getAccountId()){
				foundSolution = true;
				if(checkSolution.getScore() > solution.getScore()){
					currentBestSolution.remove(solution);
					currentBestSolution.add(checkSolution);
				}
			}
		}
		if(!foundSolution){
			currentBestSolution.add(checkSolution);
		}
	}

	public void isBestSolutionForPuzzle(Solution checkSolution, List<Solution> currentBestSolution){
		boolean foundSolution = false;

		for (Solution solution: currentBestSolution) {
			if(solution.getPuzzle().getId() == checkSolution.getPuzzle().getId()){
				foundSolution = true;
				if(checkSolution.getScore() > solution.getScore()){
					currentBestSolution.remove(solution);
					currentBestSolution.add(checkSolution);
				}
			}
		}
		if(!foundSolution){
			currentBestSolution.add(checkSolution);
		}
	}

	public void updateScoreForMaker(Puzzle puzzle, Solution newSolution){
		List<Solution> solutions = solutionDAO.getSolutionOfPuzzle(puzzle, puzzle.getAccount());
		List<Solution> bestSolutions = new ArrayList<>();
		solutions.add(newSolution);

		for (Solution solution: solutions) {
			isBestSolution(solution, bestSolutions);
		}

		Solution solution = solutionDAO.getSolutionMyPuzzle(puzzle, puzzle.getAccount());

		if(solution == null){
			return;
		}
		if(bestSolutions.size() == 0){
			puzzle.getAccount().getScore().setScoreARelativeScore(1);
			puzzle.getAccount().getScore().setScoreASolutionRanking(1);
			puzzle.getAccount().getScore().setScoreASolutionVariation(1);
			return;
		}

		bestSolutions.sort(Collections.reverseOrder(Comparator.comparingInt(Solution::getScore)));

		Score score = puzzle.getAccount().getScore();

		score.setScoreARelativeScore(Math.min(1.0,  (double)solution.getScore() / (double)bestSolutions.get(0).getScore()));

		double valuePerRank = 1.0 / (bestSolutions.size());
		score.setScoreASolutionRanking(1.0 - valuePerRank * calculatePositionInSolutions(solution, bestSolutions));

		score.setScoreASolutionVariation((double)calculateUniqueSolutions(bestSolutions) / (double)bestSolutions.size());

	}
	public void calculateBscore(Account account, Solution newSolution){
		List<Solution> solutions = solutionDAO.getBestSolutionsOfAccount(account);
		List<Solution> bestSolutions = new ArrayList<>();

		for (Solution solution: solutions) {
			isBestSolutionForPuzzle(solution, bestSolutions);
		}

		int amount = accountService.getAccountsWithPuzzleCount();

		double score = (double)bestSolutions.size()/(double)amount;
		account.getScore().setScoreBSolutionScore(score);

		List<Solution> makerSolutions = solutionDAO.getSolutionOfMaker();
		double solutionEnergyLeft = 0;
		int solutionAmount = 0;
		for (Solution solution: bestSolutions) {
			for(Solution makerSolution: makerSolutions){
				if(solution.getPuzzle().getId() == makerSolution.getPuzzle().getId()){
					solutionAmount++;
					solutionEnergyLeft += (Math.min(1,  (double) solution.getScore() / (double)makerSolution.getScore()));
				}
			}
		}
		solutionEnergyLeft /= (double)solutionAmount;
		account.getScore().setScoreBleftEnergy(solutionEnergyLeft);
	}

	public double calculatePositionInSolutions(Solution checkSolution, List<Solution> solutions){
		for (int i = 0; i < solutions.size() ; i++) {
			if(solutions.get(i).getScore() < checkSolution.getScore()){
				return i;
			}
		}
		return solutions.size();
	}

	public int calculateUniqueSolutions(List<Solution> solutions){
		Set<String> uniqueSolutions = new HashSet<>();

		for (int i = 0; i < solutions.size() ; i++) {
			uniqueSolutions.add(solutions.get(i).getHash());
		}
		return uniqueSolutions.size();
	}

    public Solution getSolution() {
        return solutionDAO.getSolutionById(1);
    }

    public void saveSolution(Solution solution) {
		Account account = solution.getPuzzle().getAccount();
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			solution.setHash(DatatypeConverter.printHexBinary(messageDigest.digest(solution.getCode().getBytes())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		solutionDAO.saveSolution(solution);
		updateScore(solution);

	}
    public void saveSolutionForMaker(Solution solution){
        solutionDAO.saveSolutionForMaker(solution);
    }

    public int getAttempts(int puzzleid, int accountid) {
        return solutionDAO.getAttempts(puzzleid, accountid);
    }

    public Solution getBestSolution(int id, Account accountModel) {
        return solutionDAO.getBestSolution(id, accountModel.getAccountId());
    }

    public ArrayList<Solution> getDoneList(int accountid) {
        return solutionDAO.getDoneList(accountid);
    }

    public ArrayList<Solution> getTriedList(int accountid) {
        return solutionDAO.getTriedList(accountid);
    }

    public ArrayList<Solution> getdoneAttemptsLeft(int accountid) {
        return solutionDAO.getdoneNoAttemptsLeft(accountid);
    }

    public ArrayList<Solution> getFailList(int accountid) {
        return solutionDAO.getFailList(accountid);
    }

    public int getMAXATTEMPTS() {
        return MAXATTEMPTS;
    }


}
