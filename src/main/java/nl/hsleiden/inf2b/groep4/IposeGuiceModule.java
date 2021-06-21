package nl.hsleiden.inf2b.groep4;

import com.google.inject.AbstractModule;
import org.hibernate.SessionFactory;

public class IposeGuiceModule extends AbstractModule {

	private final HbnBundle hbnBundle;

	public IposeGuiceModule(HbnBundle hbnBundle) {
		this.hbnBundle = hbnBundle;
	}

	@Override
	protected void configure() {
		bind(SessionFactory.class).toInstance(hbnBundle.getSessionFactory());
	}

}
