package nl.hsleiden.inf2b.groep4.account;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;

@Singleton
public class AccountService {
	private AccountDAO accountDAO;
	private static AccountService accountService;

	@Inject
	public AccountService(AccountDAO accountDAO) {
		accountService = this;
		this.accountDAO = accountDAO;
	}

	public Account getAccountById(int userid) {

		return accountDAO.getUserByUserId(userid);
	}

	public int getAccountsWithPuzzleCount(){
		return accountDAO.getPuzzleAmount();
	}
	public void updateAccountForScores(Account account1, Account account2){
		accountDAO.updateAccount(account1, account2);
	}
	public ArrayList<Account> getAccountNamePuzzleID() {
		return accountDAO.getAccountNamePuzzleIDList();
	}

	public void updateAccount(Account account) {
		accountDAO.updateAccountPuzzle(account);
	}

	public static AccountService getAccountService() {
		return accountService;
	}
}
