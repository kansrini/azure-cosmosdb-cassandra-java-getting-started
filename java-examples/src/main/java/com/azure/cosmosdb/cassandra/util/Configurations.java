package com.azure.cosmosdb.cassandra.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configurations {
    private static String PROPERTY_FILE = "config.properties";
    private static Properties prop = null;

    private void loadProperties() throws IOException {
        InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE);
        if (input == null) {
            System.out.println("Sorry, unable to find " + PROPERTY_FILE);
            return;
        }
        prop = new Properties();
        prop.load(input);
    }

    public String getProperty(String propertyName) throws IOException {
        if (prop == null) {
            loadProperties();
        }
        return prop.getProperty(propertyName);

    }
}
