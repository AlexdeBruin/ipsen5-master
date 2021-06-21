package nl.hsleiden.inf2b.groep4.login;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import nl.hsleiden.inf2b.groep4.account.Account;
import nl.hsleiden.inf2b.groep4.account.AccountDAO;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Objects;

import static java.util.Collections.singletonMap;

@Singleton
public class LoginService {

	private static LoginService loginService;
	private AccountDAO accountDAO;
	private String rsaJsonWebKey;
	private JwtConsumer jwtConsumer;

	@Inject
	public LoginService(AccountDAO userDAO) {
		loginService = this;
		this.accountDAO = userDAO;
		this.generateKey();
		byte[] tokenSecret = new byte[0];
		try {
			tokenSecret = getJwtTokenSecret();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		jwtConsumer = new JwtConsumerBuilder()
				.setRequireExpirationTime()
				.setExpectedSubject("Token recieved when authenticated")
				.setExpectedIssuer("IposeApplication")
				.setExpectedAudience("IposeFrontend")
				.setVerificationKey(new HmacKey(tokenSecret))
				.setRelaxVerificationKeyValidation()
				//.setJwsAlgorithmConstraints(new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST, AlgorithmIdentifiers.RSA_USING_SHA256))
				.build();
	}

	public static LoginService getLoginService() {
		return loginService;
	}

	private void generateKey() {
		this.rsaJsonWebKey = "dfwzsdzwh823zebdwdz772632gdsdasdw334tegbd";
	}

	public byte[] getJwtTokenSecret() throws UnsupportedEncodingException {
		return rsaJsonWebKey.getBytes("UTF-8");
	}

	public Response tryLogin(Credentials credentials) {
		Account attemptingLogin = accountDAO.getUserByUsername(credentials.getUsername());
		if (attemptingLogin == null) {
			return Response.status(Response.Status.FORBIDDEN).type(MediaType.APPLICATION_JSON).entity(singletonMap("Message", "Login credentials do not match")).build();
		} else {
			if (Objects.equals(attemptingLogin.getPassword(), credentials.getPassword()) && !nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("closed")) {
				return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(this.createToken(attemptingLogin)).build();
			} else if (Objects.equals(attemptingLogin.getPassword(), credentials.getPassword()) && nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("closed") && attemptingLogin.getAccountRole().getRoleId() == 3) {
				return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(this.createToken(attemptingLogin)).build();
			}
				return Response.status(Response.Status.FORBIDDEN).type(MediaType.APPLICATION_JSON).entity(singletonMap("Message", "Login credentials do not match")).build();

		}
	}

	public Map<String, String> createToken(Account userToLogin) {
		try {
			byte[] tokenSecret = new byte[0];
			try {
				tokenSecret = getJwtTokenSecret();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			JwtClaims claims = new JwtClaims();
			claims.setIssuer("IposeApplication");
			claims.setAudience("IposeFrontend");
			claims.setSubject("Token recieved when authenticated");
			claims.setClaim("userid", userToLogin.getAccountId());
			claims.setExpirationTimeMinutesInTheFuture(240);

			JsonWebSignature jws = new JsonWebSignature();
			jws.setPayload(claims.toJson());
			jws.setKey(new HmacKey(tokenSecret));
			jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
			String token = jws.getCompactSerialization();
			return singletonMap("token", token);
		} catch (JoseException e) {
			//	e.printStackTrace();
		}
		return null;
	}
	//return Response.status(Response.Status.FORBIDDEN).entity(singletonMap("message", "2")).build(); }

	public Response validateToken(String token) {
		try {
			JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
			return Response.status(Response.Status.OK).entity(singletonMap("message", "1")).build();
		} catch (InvalidJwtException e) {
			//if (e.hasExpired()) {
			e.printStackTrace();
			return Response.status(Response.Status.FORBIDDEN).entity(singletonMap("message", "2")).build();
			//}else{
			//}
		}
	}

	public Response getUser() {
		return Response.status(Response.Status.FORBIDDEN).entity(singletonMap("message", "2")).build();
	}

	public JwtConsumer getJwtConsumer() {
		return jwtConsumer;
	}

	public Response changePassword(Account Account, String password) {
		Account changeAccount = Account;
		changeAccount.setPassword(password);
		changeAccount.setRequiresPasswordChange(false);
		this.accountDAO.changePassword(changeAccount);
		return Response.status(Response.Status.OK).build();
	}

}
