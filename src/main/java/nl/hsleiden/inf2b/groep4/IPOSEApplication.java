package nl.hsleiden.inf2b.groep4;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import nl.hsleiden.inf2b.groep4.adminDatabase.AutomaticBackUpService;
import nl.hsleiden.inf2b.groep4.adminDatabase.BackUpConfig;
import nl.hsleiden.inf2b.groep4.adminDatabase.BackupDAO;
import nl.hsleiden.inf2b.groep4.environmentStatus.StatusChecker;
import nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO;
import nl.hsleiden.inf2b.groep4.account.Account;
import nl.hsleiden.inf2b.groep4.account.AccountService;
import nl.hsleiden.inf2b.groep4.login.AuthService;
import nl.hsleiden.inf2b.groep4.login.JwtAuthFilter;
import nl.hsleiden.inf2b.groep4.login.LoginService;
import nl.hsleiden.inf2b.groep4.persistance.ConnectionPool;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class IPOSEApplication extends Application<IPOSEConfiguration> {

	private GuiceBundle guiceBundle;
	private HbnBundle hibernate;

	public static void main(final String[] args) throws Exception {
		new IPOSEApplication().run(args);
	}


	@Override
	public String getName() {
		return "IPOSE";
	}

	@Override
	public void initialize(final Bootstrap<IPOSEConfiguration> bootstrap) {
		bootstrap.addBundle(new ConfiguredAssetsBundle("/assets", "/", "index.html"));
		hibernate = new HbnBundle();
		bootstrap.addBundle(hibernate);
		bootstrap.addBundle(GuiceBundle.builder()
				.enableAutoConfig(getClass().getPackage().getName())
				.modules(new IposeGuiceModule(hibernate))
				.useWebInstallers()
				.build());
	}

	@Override
	public void run(final IPOSEConfiguration configuration,
	                final Environment environment) {

		// Redirect 404 errors to /
		ErrorPageErrorHandler errorHandler = new ErrorPageErrorHandler();
		errorHandler.addErrorPage(404, "/");
		environment.getApplicationContext().setErrorHandler(errorHandler);
		environment.jersey().setUrlPattern("/api");


		final ConnectionPool connectionPool = new ConnectionPool("org.postgresql.driver", "JDBC:Postgresql://localhost:/test", "postgres", "ipose12345");
		final StatusChecker checker = new StatusChecker(StatusDAO.getStatusDAO());
		final BackUpConfig config = new BackUpConfig();
		final AutomaticBackUpService service = new AutomaticBackUpService(config);
		//final BackupDAO backupDAO = new BackupDAO(connectionPool);

        // Used for recieving pdf's backend
        environment.jersey().register(MultiPartFeature.class);
        environment.jersey().register(new JsonProcessingExceptionMapper(true));

        //used for jwtauthfilter
		final LoginService loginService = LoginService.getLoginService();
		final AuthService authService = new AuthService(AccountService.getAccountService());



		//used for the auth
		environment.jersey().register(new AuthValueFactoryProvider.Binder<>(Account.class));
        environment.jersey().register(RolesAllowedDynamicFeature.class);

		environment.jersey().register(new AuthDynamicFeature(
				new JwtAuthFilter.Builder<Account>()
						.setJwtConsumer(loginService.getJwtConsumer())
						.setRealm("realm")
						.setPrefix("Bearer")
						.setAuthenticator(authService)
						.setAuthorizer(authService)
						.buildAuthFilter()));

//        Interpreter Interpreterinterpreter = new Interpreter(new Solution(code));
//        winterpreter.start();

    }

}
