package nl.hsleiden.inf2b.groep4.account;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class AccountResource {
	private AccountDAO accountDAO;

	@Inject
	public AccountResource(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	@GET
	@UnitOfWork
	@Path("/allAccounts")
	@RolesAllowed("GROUP")
	public List<Account> getAllAccounts() {
		List<Account> accounts = accountDAO.getAllAccounts();
		accounts.forEach(account -> account.setPassword(null));

		return accounts;
	}


	@GET
	@Path("/{name}")
	@UnitOfWork
	@RolesAllowed("GROUP")
	public Account getAccountByName(@PathParam("name") String name, @Auth Account account) {
			return this.accountDAO.getAccountByUsername(name).orElseThrow(() -> notFoundException(name));

	}

	@POST
	@RolesAllowed("ADMIN")
	@UnitOfWork
	public void addAccount(Account account) {
		if (this.accountDAO.accountWithUsernameExists(account.getUsername())) {
			throw accountAlreadyExistsException(account.getUsername());
		} else {
			this.accountDAO.createAccount(account);
		}
	}

	@POST
	@Path("/multiple")
	@RolesAllowed("ADMIN")
	@UnitOfWork
	public void addMultipleAccounts(Account[] accounts) {
		// Make sure there are no accounts that already exist
		for (Account account : accounts) {
			if (this.accountDAO.accountWithUsernameExists(account.getUsername())) {
				throw accountAlreadyExistsException(account.getUsername());
			}
		}

		Arrays.stream(accounts).forEach(account ->
				this.accountDAO.createAccount(account));
	}

	@PUT
	@Path("/{name}")
	@RolesAllowed("GROUP")
	@UnitOfWork
	public void updateAccount(@PathParam("name") String name, Account updatedAccount, @Auth Account account) {
		if (isGroupAccount(account) && !account.getName().equals(updatedAccount.getName())) {
			throw new WebApplicationException(
					"Only admins can update the information of other accounts",
					Response.Status.FORBIDDEN
			);
		} else {
			this.accountDAO.updateAccount(updatedAccount);
		}
	}

	@DELETE
	@Path("/{name}")
	@RolesAllowed("ADMIN")
	@UnitOfWork
	public void deleteAccountByName(@PathParam("name") String name) {
		this.accountDAO.delete(
				this.accountDAO.getAccountByUsername(name)
						.orElseThrow(() -> notFoundException(name))
		);
	}

	private WebApplicationException notFoundException(String element) {
		return new WebApplicationException(
				String.format("No account with name %s", element),
				Response.Status.NOT_FOUND
		);
	}

	private WebApplicationException badRequestException(String message) {
		return new WebApplicationException(
				message,
				Response.Status.BAD_REQUEST
		);
	}

	private WebApplicationException accountAlreadyExistsException(String accountName) {
		return badRequestException(
				String.format(
						"Account with name %s already exists",
						accountName
				)
		);
	}

	private boolean isGroupAccount(Account account) {
		return account.getAccountRole().getRoleName().equals("GROUP");
	}

}
