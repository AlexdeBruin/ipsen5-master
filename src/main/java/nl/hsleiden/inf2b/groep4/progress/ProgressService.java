package nl.hsleiden.inf2b.groep4.progress;

import com.google.inject.Inject;
import nl.hsleiden.inf2b.groep4.account.Account;
import nl.hsleiden.inf2b.groep4.account.AccountDAO;
import nl.hsleiden.inf2b.groep4.solution.SolutionDAO;

import javax.ws.rs.core.Response;

public class ProgressService {
	private AccountDAO accountDAO;
	private SolutionDAO solutionDAO;
	private int puzzleidToDelete;
	@Inject
	public ProgressService(AccountDAO accountDAO, SolutionDAO solutionDAO) {
		this.accountDAO = accountDAO;
		this.solutionDAO = solutionDAO;
	}

	public Response getAmountSolvedPuzzle(int accountid) {
		return Response.status(Response.Status.OK).entity(solutionDAO.getTotalSolved(accountid)).build();
	}

	public Response deletePuzzle(int accountId) {
		Account accountToUpdate = accountDAO.getUserByUserId(accountId);
		try{
			puzzleidToDelete = accountToUpdate.getPuzzle().getId();
			accountToUpdate.setPuzzle(null);
			accountDAO.updateAccount(accountToUpdate);
		}catch (Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).build();
	}
}
