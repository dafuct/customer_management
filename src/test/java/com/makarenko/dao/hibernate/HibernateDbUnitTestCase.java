package com.makarenko.dao.hibernate;

import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.hibernate.cfg.Configuration;

class HibernateDbUnitTestCase {

  private JdbcDatabaseTester iDatabaseTester;

  HibernateDbUnitTestCase() throws ClassNotFoundException {
    Configuration configuration = new Configuration().configure();
    System.setProperty(PropertiesBasedJdbcDatabaseTester
        .DBUNIT_DRIVER_CLASS, configuration.getProperty("hibernate.connection.driver_class"));
    System.setProperty(PropertiesBasedJdbcDatabaseTester
        .DBUNIT_CONNECTION_URL, configuration.getProperty("hibernate.connection.url"));
    System.setProperty(PropertiesBasedJdbcDatabaseTester
        .DBUNIT_USERNAME, configuration.getProperty("hibernate.connection.username"));
    System.setProperty(PropertiesBasedJdbcDatabaseTester
        .DBUNIT_PASSWORD, configuration.getProperty("hibernate.connection.password"));

    iDatabaseTester = new JdbcDatabaseTester(
        configuration.getProperty("hibernate.connection.driver_class"),
        configuration.getProperty("hibernate.connection.url"),
        configuration.getProperty("hibernate.connection.username"),
        configuration.getProperty("hibernate.connection.password"));
  }

  void setInitialData() throws Exception {
    IDataSet setData = new FlatXmlDataSetBuilder().build(Thread
        .currentThread().getContextClassLoader()
        .getResourceAsStream("dataset/client.xml"));
    iDatabaseTester.setDataSet(setData);
    iDatabaseTester.onSetup();
  }
}
