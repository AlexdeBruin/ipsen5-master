package test.solution;

import io.dropwizard.testing.junit.DAOTestRule;
import nl.hsleiden.inf2b.groep4.account.Account;
import nl.hsleiden.inf2b.groep4.account.AccountDAO;
import nl.hsleiden.inf2b.groep4.puzzle.PuzzleDAO;
import nl.hsleiden.inf2b.groep4.puzzle.block.Puzzle;
import nl.hsleiden.inf2b.groep4.solution.Solution;
import nl.hsleiden.inf2b.groep4.solution.SolutionDAO;
import org.hibernate.SessionFactory;
import org.junit.*;
import test.DaoConnection;

import java.util.ArrayList;

// TODO: 27-6-2018 this still uses hardcoded data so that has to be fixed to make the test work
public class SolutionDAOTest {

	@Rule
	public DAOTestRule database = DaoConnection.DATABASE;

	private SolutionDAO solutionDAO;
	private AccountDAO accountDAO;
	private PuzzleDAO puzzleDAO;
	private Account puzzleTestAccount, testAccount1, testAccount2, testAccount3, testAccount4, testAccount5;
	private Puzzle testPuzzle1;

	@Before
	public void setUp() {
		SessionFactory sessionFactory = database.getSessionFactory();
		solutionDAO = new SolutionDAO(sessionFactory);
		accountDAO = new AccountDAO(sessionFactory);
		puzzleDAO = new PuzzleDAO(sessionFactory);
		insertTestAccounts();
		insertTestPuzzle();
	}

	private void insertTestAccounts() {
		if(!accountDAO.getAccountByUsername("test1").isPresent()) {
			Account a = new Account();
			a.setUsername("puzzle");
			a.setPassword("123456789");
			accountDAO.createAccount(a);
			a = new Account();
			a.setUsername("test1");
			a.setPassword("123456789");
			accountDAO.createAccount(a);
			a = new Account();
			a.setUsername("test2");
			a.setPassword("123456789");
			accountDAO.createAccount(a);
			a = new Account();
			a.setUsername("test3");
			a.setPassword("123456789");
			accountDAO.createAccount(a);
			a = new Account();
			a.setUsername("test4");
			a.setPassword("123456789");
			accountDAO.createAccount(a);
			a = new Account();
			a.setUsername("test5");
			a.setPassword("123456789");
			accountDAO.createAccount(a);
		}
		if(testAccount1 == null) {
			puzzleTestAccount = accountDAO.getAccountByUsername("puzzle").get();
			testAccount1 = accountDAO.getAccountByUsername("test1").get();
			testAccount2 = accountDAO.getAccountByUsername("test2").get();
			testAccount3 = accountDAO.getAccountByUsername("test3").get();
			testAccount4 = accountDAO.getAccountByUsername("test4").get();
			testAccount5 = accountDAO.getAccountByUsername("test5").get();
		}
	}

	private void insertTestPuzzle() {
		if(puzzleDAO.getPuzzle(1) == null) {
			testPuzzle1 = new Puzzle();
			testPuzzle1.setSandbox(true);
			testPuzzle1.setAccount(puzzleTestAccount);
			puzzleDAO.savePuzzle(testPuzzle1);
		}
		if(testPuzzle1 == null) {
			testPuzzle1 = puzzleDAO.getPuzzle(1);
		}
	}

	@Test
	public void testNoAttempts() {
		int attempts = solutionDAO.getAttempts(0,0);
		Assert.assertEquals(0, attempts);
	}

	@Test
	public void insertSolution() {
		Solution s = new Solution();
		s.setAccount(testAccount1);
		s.setPuzzle(testPuzzle1);
		s.setSolutionid(1);
		solutionDAO.saveSolution(s);
		Assert.assertEquals(s, solutionDAO.getSolutionById(1));
	}

	@Test
	public void testTriedList() {
		if(solutionDAO.getMAXATTEMPTS() > 1) {
			ArrayList<Solution> triedlist = solutionDAO.getTriedList(testAccount2.getAccountId());
			Assert.assertEquals(0, triedlist.size());
			Solution s = new Solution();
			s.setAccount(testAccount2);
			s.setPuzzle(testPuzzle1);
			s.setSucces(false);
			solutionDAO.saveSolution(s);
			triedlist = solutionDAO.getTriedList(testAccount2.getAccountId());
			Assert.assertEquals(1, triedlist.size());
		}
	}

	@Test
	public void testFailList() {
		Solution s;
		for(int i = 0; i < solutionDAO.getMAXATTEMPTS(); i++) {
			s = new Solution();
			s.setAccount(testAccount3);
			s.setPuzzle(testPuzzle1);
			s.setSucces(false);
			s.setScore(0);
			solutionDAO.saveSolution(s);
		}
		ArrayList<Solution> failList = solutionDAO.getFailList(testAccount3.getAccountId());
		Assert.assertEquals(1, failList.size());
	}

	@Test
	public void testDoneNoAttemptsLeftList() {
		Solution s;
		for(int i = 0; i < solutionDAO.getMAXATTEMPTS(); i++) {
			s = new Solution();
			s.setAccount(testAccount5);
			s.setPuzzle(testPuzzle1);
			s.setSucces(true);
			s.setScore(0);
			solutionDAO.saveSolution(s);
		}
		ArrayList<Solution> doneList = solutionDAO.getdoneNoAttemptsLeft(testAccount5.getAccountId());
		Assert.assertEquals(1, doneList.size());
	}

	@Test
	public void testDoneList() {
		Solution s = new Solution();
		s.setAccount(testAccount4);
		s.setPuzzle(testPuzzle1);
		s.setSucces(true);
		s.setScore(0);

		solutionDAO.saveSolution(s);
		ArrayList<Solution> doneList = solutionDAO.getDoneList(testAccount4.getAccountId());
		Assert.assertEquals(1, doneList.size());
	}

}
