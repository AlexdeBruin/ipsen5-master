package test.login;

import io.dropwizard.testing.junit.DAOTestRule;
import nl.hsleiden.inf2b.groep4.account.Account;
import nl.hsleiden.inf2b.groep4.account.AccountDAO;
import nl.hsleiden.inf2b.groep4.login.LoginService;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import test.DaoConnection;

import static org.junit.Assert.assertEquals;

public class loginServiceTest {
	@Rule
	public DAOTestRule database = DaoConnection.DATABASE;

	private Account a;
	private LoginService loginService;
	private AccountDAO accountDAO;


	@Before
	public void setup() throws Exception {
		accountDAO = new AccountDAO(database.getSessionFactory());
		loginService = new LoginService(accountDAO);
		a = new Account();
		a.setUsername("test");
		a.setPassword("testing1234!");
		accountDAO.createAccount(a);
	}

	@Test
	public void testChangePassword(){
		loginService.changePassword(a, "wachtwoord12!");
		Account resultAccount = accountDAO.getUserByUsername(a.getUsername());
		assertEquals("wachtwoord12!", resultAccount.getPassword());
	}

	@After
	public void cleanup(){
		accountDAO.delete(a);
	}
}
