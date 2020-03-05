package com.nixsolutions.listener;

import static com.nixsolutions.dao.AbstractJdbcDao.createConnection;

import com.nixsolutions.property.ReaderSqlFile;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletContext implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    try (Statement statement = createConnection()
        .createStatement()) {
      statement.executeUpdate(new ReaderSqlFile().executeSqlScript());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
  }
}
