package nl.hsleiden.inf2b.groep4.account;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class AccountDAO extends AbstractDAO<Account> {

	SessionFactory sessionFactory;

	@Inject
	public AccountDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
		this.sessionFactory = sessionFactory;
	}

	public List<Account> getAllAccounts() {

		List<Account> accounts = list(namedQuery("nl.hsleiden.inf2b.groep4.account.Account.findAll"));
		currentSession().getSession().clear();
		return accounts;
	}

	public Account getUserByUsername(String username) {
		Query query = namedQuery("nl.hsleiden.inf2b.groep4.account.Account.findByUsername").setParameter("username", username);
		Account account = (Account) query.uniqueResult();
		return account;

	}


	public Account getUserByUserId(int Accountid) {
		Session session = null;
		Account model = null;
		try {
			session = sessionFactory.openSession();
			model = session.load(Account.class, Accountid);
			Hibernate.initialize(model);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return model;

	}

	// TODO replace the other one with this
	public Optional<Account> getAccountByUsername(String username) {
		return Optional.ofNullable(
				(Account)
						currentSession()
								.createQuery("select a from  Account a where a.username = :username")
								.setParameter("username", username)
								.uniqueResult()
		);
	}

	public boolean accountWithUsernameExists(String username) {
		return currentSession()
				.createQuery("select a from Account a where username = :username")
				.setParameter("username", username)
				.uniqueResult() != null;
	}

	public void createAccount(Account account) {
		Score score = new Score();
		account.setScore(score);
		persist(account);
	}

	public void updateAccount(Account account) {
		persist(account);
	}

	public void updateAccount(Account account1, Account account2) {
		currentSession().update(account1);
		currentSession().update(account2);
		currentSession().getTransaction().commit();


	}

	public int getPuzzleAmount() {
		return ((Long) currentSession().createQuery("select count(*) from Account WHERE puzzle_id <> NULL").uniqueResult()).intValue();
	}

	public void delete(Account account) {
		currentSession().delete(account);
	}

	public void changePassword(Account changeAccount) {
		persist(changeAccount);

	}

	public void updateAccountPuzzle(Account account) {
		Session session = currentSession().getSession();
		session.saveOrUpdate(account);
		session.getTransaction().commit();
	}

	public ArrayList<Account> getAccountNamePuzzleIDList() {
		Query query = namedQuery("nl.hsleiden.inf2b.groep4.account.Account.getPuzzles");
		List<Account> list = query.list();
		currentSession().clear();
		ArrayList<Account> filteredList = new ArrayList<>();
		for (Account a : list) {
			Account tmp = new Account();
			tmp.setUsername(a.getUsername());
			tmp.setAccountId(a.getAccountId());
			tmp.setPuzzle(a.getPuzzle());
			tmp.getPuzzle().setLevelGrid(null);
			tmp.getPuzzle().setCostCard(null);
			filteredList.add(tmp);
		}
		return filteredList;
	}
}
