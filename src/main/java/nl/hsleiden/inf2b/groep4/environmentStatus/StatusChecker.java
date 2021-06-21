package nl.hsleiden.inf2b.groep4.environmentStatus;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

@Singleton
public class StatusChecker {

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private StatusDAO dao;

	@Inject
	public StatusChecker(StatusDAO dao) {
		this.dao = dao;
		this.startChecking(this.dao);
	}

	private void startChecking(StatusDAO dao) {
		final Runnable checker = new Runnable() {
			@Override
			public void run() {
				dao.statusChecker();
			}
		}; final ScheduledFuture<?> checkFuture = scheduler.scheduleAtFixedRate(checker, 5, 5, MINUTES);
//		scheduler.schedule(new Runnable() {
//            public void run() { checkFuture.cancel(true); }
//        }, 7, DAYS); // er wordt na 7 dagen geen back up meer gemaakt
	}
}
