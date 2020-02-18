package com.dao;

import com.entity.Role;
import com.entity.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao extends AbstractJdbcDao implements UserDao {

    @Override
    public void create(User user) {
        if (user == null) {
            throw new NullPointerException();
        }

        String sql = "INSERT INTO user ("
            + "first_name, last_name, login, password, email, birthday, role_id) "
            + "values (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = createConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, user.getFirstName());
                statement.setString(2, user.getLastName());
                statement.setString(3, user.getLogin());
                statement.setString(4, user.getPassword());
                statement.setString(5, user.getEmail());

                java.util.Date birthday = user.getBirthday();
                Date dateSql = new Date(birthday.getTime());
                statement.setDate(6, dateSql);

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
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        if (user == null) {
            throw new NullPointerException();
        }

        if (!existUserById(user.getId())) {
            throw new IllegalArgumentException();
        }

        String sql = "UPDATE user SET first_name = ?, last_name = ?, login = ?, "
            + "password = ?, email = ?, birthday = ?, role_id = ? "
            + "WHERE id = ?";
        try (Connection connection = createConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, user.getFirstName());
                statement.setString(2, user.getLastName());
                statement.setString(3, user.getLogin());
                statement.setString(4, user.getPassword());
                statement.setString(5, user.getEmail());

                java.util.Date birthday = user.getBirthday();
                Date dateSql = new Date(birthday.getTime());
                statement.setDate(6, dateSql);

                statement.setLong(7, user.getRole().getId());
                statement.setLong(8, user.getId());
                statement.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException eRollBack) {
                    throw new RuntimeException(eRollBack);
                }
                throw new RuntimeException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(User user) {
        if (user == null) {
            throw new NullPointerException();
        }

        if (!existUserById(user.getId())) {
            throw new IllegalArgumentException();
        }

        String sql = "DELETE FROM user WHERE id = ?";
        try (Connection connection = createConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, user.getId());
                statement.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException eRollBack) {
                    throw new RuntimeException(eRollBack);
                }
                throw new RuntimeException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try (Connection connection = createConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                userList.add(new User(
                    resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("login"),
                    resultSet.getString("password"),
                    resultSet.getString("email"),
                    resultSet.getDate("birthday"),
                    new Role(resultSet.getLong("role_id"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    @Override
    public User findByLogin(String login) {
        if (login == null) {
            throw new NullPointerException();
        }

        if (!existUserByLogin(login)) {
            throw new IllegalArgumentException();
        }

        User user = new User();
        String sql = "SELECT * FROM user WHERE login = ?";
        try (Connection connection = createConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));

                Date birthday = resultSet.getDate("birthday");
                java.util.Date utilBirthday = new Date(birthday.getTime());
                user.setBirthday(utilBirthday);

                user.setRole(new Role(resultSet.getLong("role_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        if (email == null) {
            throw new NullPointerException();
        }

        if (!existUserByEmail(email)) {
            throw new IllegalArgumentException();
        }

        User user = new User();
        String sql = "SELECT * FROM user WHERE email = ?";
        try (Connection connection = createConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setLastName(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));

                Date birthday = resultSet.getDate("birthday");
                java.util.Date utilBirthday = new Date(birthday.getTime());
                user.setBirthday(utilBirthday);

                user.setRole(new Role(resultSet.getLong("role_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    private boolean existUserById(Long id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        try (Connection connection = createConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private boolean existUserByLogin(String login) {
        String sql = "SELECT * FROM user WHERE login = ?";
        try (Connection connection = createConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private boolean existUserByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        try (Connection connection = createConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
