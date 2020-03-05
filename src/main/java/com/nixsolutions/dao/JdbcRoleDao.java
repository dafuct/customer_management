package com.nixsolutions.dao;

import static com.nixsolutions.dao.AbstractJdbcDao.createConnection;


import com.nixsolutions.entity.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcRoleDao implements RoleDao {

    @Override
    public void create(Role role) {
        if (role == null) {
            throw new NullPointerException();
        }

        String sql = "INSERT INTO role (name) VALUES (?) ";
        try (Connection connection = createConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, role.getName());
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

    @Override
    public void update(Role role) {
        if (role == null) {
            throw new NullPointerException();
        }

        String sql = "UPDATE role SET name = ? WHERE id = ?";
        try (Connection connection = createConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, role.getName());
                statement.setLong(2, role.getId());
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

    @Override
    public void remove(Role role) {
        if (role == null) {
            throw new NullPointerException();
        }

        String sql = "DELETE FROM role WHERE id = ?";
        try (Connection connection = createConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, role.getId());
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

    @Override
    public List<Role> findAll() {
        List<Role> roleList = new ArrayList<>();
        String sql = "SELECT * FROM role";
        try (Connection connection = createConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                roleList.add(new Role(resultSet.getLong("id"),
                    resultSet.getString("name")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roleList;
    }

    @Override
    public Role findByName(String name) {
        if (name == null) {
            throw new NullPointerException();
        }

        Role role = new Role();
        String sql = "SELECT * FROM role WHERE name = ?";
        try (Connection connection = createConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                role.setId(resultSet.getLong("id"));
                role.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return role;
    }
}
