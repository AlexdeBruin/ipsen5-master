package nl.hsleiden.inf2b.groep4.login;

import com.google.inject.Inject;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import nl.hsleiden.inf2b.groep4.account.Account;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

	private final LoginService loginService;

	@Inject
	public LoginResource(LoginService loginService) {
		this.loginService = loginService;
	}

	@POST
	@Path("/trylogin")
	@UnitOfWork
	public Response tryLogin(Credentials credentials) {
		return this.loginService.tryLogin(credentials);
	}

	@POST
	@Path("/validateToken")
	@UnitOfWork
	public Response validateToken(String token) {
		return this.loginService.validateToken(token);
	}

	@GET
	@Path("/getaccount")
	public Account getUserForLogin(@Auth Account Account) {
		Account.setPassword(null);
		return Account;
	}

	@POST
	@Path("/changePassword")
	@UnitOfWork
	public Response changePassword(@Auth Account Account, String newPassword) {
		return this.loginService.changePassword(Account, newPassword);
	}
}