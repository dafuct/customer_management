package com.property;

import java.io.IOException;
import java.util.Properties;

public class JdbcProperties {
    private String driver;
    private String url;
    private String user;
    private String password;

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public JdbcProperties invoke() {
        Properties properties = new Properties();
        try {
            properties.load(getClass()
                .getClassLoader()
                .getResourceAsStream("db.config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        driver = properties.getProperty("db.driver");
        url = properties.getProperty("db.url");
        user = properties.getProperty("db.user");
        password = properties.getProperty("db.password");
        return this;
    }
}
