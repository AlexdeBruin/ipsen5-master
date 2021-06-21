package nl.hsleiden.inf2b.groep4.adminDatabase;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Process;
import java.util.concurrent.TimeUnit;

@Singleton
public class DatabaseService {

    private String path;
    private String workingPath;
    private String host;
    private String user;
    private String password;
    private String database;
    private String wipeFile;
    private String exportPath;
    private String query;

    private BackUpConfig config;
    private BackupDAO backupDAO;


    public DatabaseService(BackUpConfig config) {
        this.config = config;
    }

	@Inject
    public DatabaseService(BackUpConfig config, BackupDAO backupDAO) {
        this.config = config;
        this.backupDAO = backupDAO;
    }

    void createBackUp() {
        this.setVariables();

        Process process;
        ProcessBuilder processBuilder;

        try {
            processBuilder = new ProcessBuilder(workingPath+"/pg_dump","-a","--inserts", "-v", "-h", host, "-Fc", "-f", this.path, "-U", user, database);
            processBuilder.environment().put("PGPASSWORD", password);
            processBuilder.redirectErrorStream(true);
            process = processBuilder.start();
                InputStream is = process.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
					System.out.println(line);
				}
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    void restoreBackUp() {
        this.setVariables();

        Process process;
        ProcessBuilder processBuilder;

        try {
            processBuilder = new ProcessBuilder(workingPath+"/pg_restore","-Fc", "-d", database, "-U", user, "-h", host, path);
            processBuilder.environment().put("PGPASSWORD", password);
            processBuilder.redirectErrorStream(true);
            process = processBuilder.start();
                InputStream is = process.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
					System.out.println(line);
				}
        } catch(IOException ioE) {
            ioE.printStackTrace();
        }
    }
    private void setVariables(){


        this.path = config.getProperty("path");
        this.workingPath = config.getProperty("workingPath");
        this.host = config.getProperty("host");
        this.user = config.getProperty("user");
        this.password = config.getProperty("password");
        this.database = config.getProperty("database");
        this.wipeFile = config.getProperty("wipeFileLocation");
        this.exportPath = config.getProperty("exportPath");
        this.query = config.getProperty("query");
    }

    void wipeAll() {
		this.setVariables();
        this.backupDAO.wipeAll(this.user);
    }

    void exportGrades () {
        this.setVariables();
        Process process;
        ProcessBuilder processBuilder;
        try {
            processBuilder = new ProcessBuilder(workingPath+"/psql", "-d", database, "-U", user, "-h", host, "-c" + " COPY (" + query +") TO '" + exportPath + "' WITH CSV" );
            processBuilder.environment().put("PGPASSWORD", password);
            processBuilder.redirectErrorStream(true);
            process = processBuilder.start();
                InputStream is = process.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
        } catch(IOException ioE) {
            ioE.printStackTrace();
        }

    }

//    void exitSystem() throws InterruptedException{
//
//        try{
//            this.createBackUp();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            try{
//                TimeUnit.SECONDS.sleep(1);
//            } finally {
//                TimeUnit.SECONDS.sleep(2);
//                    System.exit(0);
//            }
//        }
//   }
}
