package server.controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class EnvironmentVariables {
    private static EnvironmentVariables environmentVariables = null;
    private Properties p;

    private EnvironmentVariables() {
        FileReader reader = null;
        try {
            reader = new FileReader("app.properties");
            p = new Properties();
            p.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(String key) {
        return p.getProperty(key);
    }

    public Integer getInteger(String key) {
        return Integer.parseInt(p.getProperty(key));
    }

    public static EnvironmentVariables getInstance() {
        if (environmentVariables == null)
            environmentVariables = new EnvironmentVariables();
        return environmentVariables;
    }
}
