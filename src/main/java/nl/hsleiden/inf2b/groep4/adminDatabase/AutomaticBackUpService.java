package nl.hsleiden.inf2b.groep4.adminDatabase;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

@Singleton
public class AutomaticBackUpService extends DatabaseService {


    public AutomaticBackUpService(BackUpConfig config) {
        super(config);
        this.automBackup();
    }

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void automBackup() {
        final Runnable backUp = new Runnable() {
            @Override
            public void run() {
                AutomaticBackUpService.super.createBackUp();
            }
        };
        final ScheduledFuture<?> autoBackupHandler = scheduler.scheduleAtFixedRate(backUp,2,2, HOURS ); // elke 2 uur wordt er een back up gemaakt
        // er wordt voor een dag back ups gemaakt
//        scheduler.schedule(new Runnable() {
//            public void run() { autoBackupHandler.cancel(true); }
//        }, 7, DAYS); // er wordt na 7 dagen geen back up meer gemaakt
    }
}

