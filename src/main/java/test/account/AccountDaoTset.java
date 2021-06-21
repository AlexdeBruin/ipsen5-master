package test.account;

import io.dropwizard.testing.junit.DAOTestRule;
import nl.hsleiden.inf2b.groep4.account.Account;
import nl.hsleiden.inf2b.groep4.account.AccountDAO;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import test.DaoConnection;

import java.time.chrono.HijrahDate;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class AccountDaoTset {
	@Rule
	public DAOTestRule database = DaoConnection.DATABASE;

	private AccountDAO accountDAO;

	@Before
	public void setup() {
		initAccountDao();
		insertTestAccountIfAbsent();
	}

	@After
	public void tearDown() {

	}

	private void initAccountDao() {
		SessionFactory sessionFactory = database.getSessionFactory();
		this.accountDAO = new AccountDAO(sessionFactory);
	}

	private void insertTestAccountIfAbsent() {
		if (!this.accountDAO.getAccountByUsername("unitTestAccount").isPresent()) {
			Account testAccount = new Account();
			testAccount.setUsername("unitTestAccount");
			testAccount.setPassword("this is a very good password");
			accountDAO.createAccount(testAccount);
		}
	}

	@Test
	public void findAccountThatExists() {
		assertTrue(
				accountDAO.getAccountByUsername("unitTestAccount").isPresent()
		);
	}

	@Test
	public void findAccountThatDoesNotExist() {
		assertFalse(
				"this test fails if the tested account exists",
				accountDAO.getAccountByUsername("blahblahblah").isPresent()
		);
	}

	@Test
	public void testAccountWithUsernameThatExists() {
		assertTrue(
				accountDAO.accountWithUsernameExists("unitTestAccount")
		);
	}

	@Test
	public void testAccountWithUsernameThatDoesNotExist() {
		assertFalse(
				"this test fails if the tested account exists, really we should have used containers",
				accountDAO.accountWithUsernameExists("blahblahblah")
		);
	}

}
