package com.dao;

import com.property.JdbcProperties;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public abstract class AbstractJdbcDao {

    private BasicDataSource dataSource;

    Connection createConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    public DataSource getDataSource() {
        JdbcProperties properties = new JdbcProperties().invoke();
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(properties.getDriver());
            dataSource.setUrl(properties.getUrl());
            dataSource.setUsername(properties.getUser());
            dataSource.setPassword(properties.getPassword());

            dataSource.setMinIdle(100);
            dataSource.setMaxIdle(1000);
        }
        return dataSource;
    }
}
