package com.cashregister.model.dao;

import com.cashregister.model.entity.Role;
import com.cashregister.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO extends AbstractDAO<Long, User> {
    private static final String SELECT_ALL_USER = "SELECT users.id, users.login, users.passwd, roles.roles_name AS role FROM users AS users LEFT JOIN roles AS roles ON users.roles_id = roles.id;";
    private static final String SELECT_USER_BY_ID = "SELECT users.id, users.login, users.passwd, roles.roles_name AS role FROM users AS users LEFT JOIN roles AS roles ON users.roles_id = roles.id WHERE users.id = ?;";

    private static final String SELECT_USER_BY_LOGIN = "SELECT users.id, users.login, users.passwd, roles.roles_name AS role FROM users AS users LEFT JOIN roles AS roles ON users.roles_id = roles.id WHERE users.login = ?;";
    private static final String CREATE_USER = "INSERT INTO users (login, passwd, roles_id) values (?,?,?);";
    private static final String UPDATE_USER = "UPDATE users SET login = ?, passwd = ?, roles_id = ? WHERE id = ?;";

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        Connection con = DBManager.getInstance().getConnection();
        Statement stmt = null;

        try {
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(SELECT_ALL_USER);
            while (resultSet.next()) {
                User user = createUser(resultSet);
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return userList;
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String login = resultSet.getString(TableFields.USERS_LOGIN);
        String passwd = resultSet.getString(TableFields.USERS_PASSWD);
        String roleName = resultSet.getString(TableFields.USERS_ROLE);

        User user = new User(id, login, passwd);
        user.setRole(Role.valueOf(roleName.toUpperCase()));

        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = null;
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(SELECT_USER_BY_ID);
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                user = createUser(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return Optional.ofNullable(user);
    }

    @Override
    public int getRecordsCount() {
        return 0;
    }

    @Override
    public boolean create(User entity) {
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, entity.getLogin());
            stmt.setString(2, entity.getPasswd());
            stmt.setLong(3, entity.getRole().ordinal() + 1);

            int updatedAmount = stmt.executeUpdate();
            if (updatedAmount > 0) {
                try (ResultSet res = stmt.getGeneratedKeys()){
                    if (res.next()) {
                        entity.setId(res.getLong(1));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    @Override
    public boolean update(User entity) {
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(UPDATE_USER);
            stmt.setString(1, entity.getLogin());
            stmt.setString(2, entity.getPasswd());
            stmt.setLong(3, entity.getRole().ordinal() + 1);
            stmt.setLong( 4, entity.getId());

            int updatedAmount = stmt.executeUpdate();
            if (updatedAmount == 0) {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    @Override
    public boolean delete(User entity) {
        return false;
    }

    public Optional<User> findByLogin(String login) {
        if (login == null) {
            return Optional.ofNullable(null);
        }
        User user = null;
        Connection con = DBManager.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(SELECT_USER_BY_LOGIN);
            stmt.setString(1, login);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                user = createUser(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return Optional.ofNullable(user);
    }

}
