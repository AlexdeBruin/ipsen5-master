package nl.hsleiden.inf2b.groep4.login;

import com.google.inject.Singleton;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.Authorizer;
import nl.hsleiden.inf2b.groep4.account.Account;
import nl.hsleiden.inf2b.groep4.account.AccountService;
import org.jose4j.jwt.consumer.JwtContext;

import java.util.Optional;

@Singleton
public class AuthService implements Authenticator<JwtContext, Account>, Authorizer<Account> {

	private final AccountService accountService;

	public AuthService(AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public java.util.Optional<Account> authenticate(JwtContext jwtContext) throws
	                                                                       AuthenticationException {
		final long userid = (long) jwtContext.getJwtClaims().getClaimValue("userid");
		int useridInt = Math.toIntExact(userid);
		Account Account = accountService.getAccountById(useridInt);
		if (Account != null) {
			return Optional.of(accountService.getAccountById(useridInt));
		}
		return Optional.empty();
	}

	@Override
	public boolean authorize(Account account, String rolename) {
		if(account.getAccountRole().getRoleName().equals("ADMIN") && rolename.equals("GROUP")){
			return true;
		}
		return (account.getAccountRole().getRoleName().equals(rolename));
	}

}
