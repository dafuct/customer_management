package com.nixsolutions.dao;

import static com.nixsolutions.dao.AbstractJdbcDao.createConnection;


import com.nixsolutions.entity.Role;
import com.nixsolutions.entity.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao {

  private static final String INSERT_SQL = "INSERT INTO client ("
      + "first_name, last_name, login, password, email, birthday, role_id)"
      + " VALUES (?, ?, ?, ?, ?, ?, ?);";
  private static final String UPDATE_SQL = "UPDATE client SET first_name = ?, "
      + "last_name = ?, login = ?, password = ?, email = ?,"
      + "birthday = ?, role_id = ? WHERE login = ?;";
  private static final String DELETE_SQL = "DELETE FROM client WHERE login = ?;";
  private static final String FIND_ALL_SQL = "SELECT * FROM client INNER JOIN role ON client.role_id = role.id;";
  private static final String FIND_BY_LOGIN_SQL = "SELECT * FROM client INNER JOIN role ON client.role_id = role.id WHERE login = ?;";
  private static final String FIND_BY_EMAIL = "SELECT * FROM client INNER JOIN role ON client.role_id = role.id WHERE email = ?;";

  public void create(User user) {

    if (user == null) {
      throw new NullPointerException();
    }

    try (Connection connection = createConnection()) {
      connection.setAutoCommit(false);
      try (PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getLogin());
        statement.setString(4, user.getPassword());
        statement.setString(5, user.getEmail());
        statement.setDate(6, Date.valueOf(user.getBirthday()));
        statement.setLong(7, user.getRole().getId());
        statement.executeUpdate();

        connection.commit();
      } catch (SQLException e) {
        try {
          connection.rollback();
        } catch (SQLException eRollBack) {
          throw new RuntimeException(eRollBack);
        }
        throw new RuntimeException(e);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void update(User user) {

    if (user == null) {
      throw new NullPointerException();
    }

    try (Connection connection = createConnection()) {
      connection.setAutoCommit(false);
      try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getLogin());
        statement.setString(4, user.getPassword());
        statement.setString(5, user.getEmail());
        statement.setDate(6, Date.valueOf(user.getBirthday()));
        statement.setLong(7, user.getRole().getId());
        statement.setString(8, user.getLogin());
        statement.execute();

        connection.commit();
      } catch (SQLException e) {
        try {
          connection.rollback();
        } catch (SQLException eRollBack) {
          throw new RuntimeException(eRollBack);
        }
        throw new RuntimeException(e);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void remove(User user) {
    try (Connection connection = createConnection()) {
      connection.setAutoCommit(false);
      try (PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
        statement.setString(1, user.getLogin());
        statement.execute();

        connection.commit();
      } catch (SQLException e) {
        try {
          connection.rollback();
        } catch (SQLException eRollBack) {
          throw new RuntimeException(eRollBack);
        }
        throw new RuntimeException(e);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public List<User> findAll() {
    List<User> list = new ArrayList<>();

    try (Connection connection = createConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL)) {
      while (resultSet.next()) {
        RoleDao roleDao = new JdbcRoleDao();
        Role role = roleDao.findByName(resultSet.getString("name"));
        User user = new User(
            resultSet.getLong("id"),
            resultSet.getString("first_name"),
            resultSet.getString("last_name"),
            resultSet.getString("login"),
            resultSet.getString("password"),
            resultSet.getString("email"),
            resultSet.getDate("birthday").toLocalDate(),
            role);
        list.add(user);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return list;
  }

  public User findByLogin(String login) {

    if (login == null) {
      throw new NullPointerException();
    }

    User user = null;

    return getUser(login, user, FIND_BY_LOGIN_SQL);
  }

  public User findByEmail(String email) {

    if (email == null) {
      throw new NullPointerException();
    }

    User user = null;

    return getUser(email, user, FIND_BY_EMAIL);
  }

  private User getUser(String value, User user, String sql) {
    try (Connection connection = createConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, value);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          RoleDao roleDao = new JdbcRoleDao();
          Role role = roleDao.findByName(resultSet.getString("name"));
          user = new User(
              resultSet.getString("first_name"),
              resultSet.getString("last_name"),
              resultSet.getString("login"),
              resultSet.getString("password"),
              resultSet.getString("email"),
              resultSet.getDate("birthday").toLocalDate(),
              role);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return user;
  }
}
