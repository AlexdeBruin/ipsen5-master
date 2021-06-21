package nl.hsleiden.inf2b.groep4.adminDatabase;

import com.google.inject.Singleton;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Singleton
public class BackUpConfig {


    private Properties configFile = new Properties();

    public BackUpConfig() {
        File configFile = new File("backupConfig.properties");
        try {
            FileInputStream inputStream = new FileInputStream(configFile);
            this.configFile.load(inputStream);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    String getProperty(String key) {
        String respons = this.configFile.getProperty(key);
        return respons;
    }
}
