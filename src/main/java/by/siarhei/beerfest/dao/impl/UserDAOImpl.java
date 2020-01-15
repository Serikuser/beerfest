package by.siarhei.beerfest.dao.impl;

import by.siarhei.beerfest.connection.ConnectionPool;
import by.siarhei.beerfest.connection.ProxyConnection;
import by.siarhei.beerfest.dao.UserDAO;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.entity.StatusType;
import by.siarhei.beerfest.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final String INSERT_LOGIN_SQL = "INSERT INTO account (login,password,email,avatar_url,role,status) VALUES (?,?,?,?,?,?)";
    private static final String COINCIDENCES_RESULT_INDEX = "coincidences";
    private static final String CHECK_USER_BY_LOGIN_EMAIL_SQL = String.format("SELECT count(*) as %s FROM account WHERE login= ? or email= ?", COINCIDENCES_RESULT_INDEX);
    private static final String CHECK_USER_BY_LOGIN_PASSWORD_SQL = String.format("SELECT count(*) as %s FROM account WHERE login= ? and password= ?", COINCIDENCES_RESULT_INDEX);
    private static final String SELECT_USER_BY_LOGIN_SQL =
                    "SELECT account.id,login,password,email,avatar_url,role.name,status.name " +
                    "FROM account " +
                    "INNER JOIN role " +
                    "ON account.role = role.id " +
                    "INNER JOIN status " +
                    "ON account.status = status.id WHERE login=?";
    private static final String UPDATE_PASSWORD_BY_LOGIN_SQL =
                    "UPDATE account " +
                    "SET password=? " +
                    "WHERE login=?";
    private static final String UPDATE_AVATAR_BY_LOGIN_SQL =
                    "UPDATE account " +
                    "SET avatar_url=? " +
                    "WHERE login=?";
    private static final String SELECT_USER_BY_ID_SQL =
                    "SELECT account.id,login,password,email,avatar_url,role.name,status.name " +
                    "FROM account " +
                    "INNER JOIN role " +
                    "ON account.role = role.id " +
                    "INNER JOIN status " +
                    "ON account.status = status.id WHERE account.id=?";

    public UserDAOImpl() {
    }

    @Override
    public User findUserByLogin(String login) {
        User user = new User();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SELECT_USER_BY_LOGIN_SQL);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                user.setId(id);
                user.setLogin(login);
                String password = resultSet.getString(3);
                user.setPassword(password);
                String eMail = resultSet.getString(4);
                user.setEmail(eMail);
                String avatarUrl = resultSet.getString(5);
                user.setAvatarUrl(avatarUrl);
                String role = resultSet.getString(6);
                user.setRole(RoleType.valueOf(role.toUpperCase()));
                String status = resultSet.getString(7);
                user.setStatus(StatusType.valueOf(status.toUpperCase()));
            }
        } catch (SQLException e) {
            logger.error(String.format("Cannot get user by login throws exception: %s", e));
        } finally {
            close(statement);
            close(connection);
            close(resultSet);
        }
        return user;
    }

    public boolean isLoginPasswordMatch(String login, String password) {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean flag = false;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CHECK_USER_BY_LOGIN_PASSWORD_SQL);
            int index = 1;
            statement.setString(index++, login);
            statement.setString(index, password);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flag = resultSet.getInt(COINCIDENCES_RESULT_INDEX) != 0;
            }
        } catch (SQLException e) {
            logger.error(String.format("Cannot check login/password exists throws exception: %s", e));
            flag = true;
        } finally {
            close(statement);
            close(connection);
            close(resultSet);
        }
        return flag;
    }

    @Override
    public boolean isExist(String login, String email) {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean flag = false;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CHECK_USER_BY_LOGIN_EMAIL_SQL);
            int index = 1;
            statement.setString(index++, login);
            statement.setString(index, email);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flag = resultSet.getInt(COINCIDENCES_RESULT_INDEX) != 0;
            }
        } catch (SQLException e) {
            logger.error(String.format("Cannot check login/eMail exists throws exception: %s", e));
            flag = true;
        } finally {
            close(statement);
            close(connection);
            close(resultSet);
        }
        return flag;
    }

    @Override
    public boolean updatePassword(String login,String newPassword) {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        boolean flag;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UPDATE_PASSWORD_BY_LOGIN_SQL);
            statement.setString(1, newPassword);
            statement.setString(2, login);
            statement.execute();
            flag = true;
        } catch (SQLException e) {
            logger.error(String.format("Cannot change password throws exception: %s", e));
            flag = false;
        } finally {
            close(statement);
            close(connection);
        }
        return flag;
    }

    @Override
    public boolean updateAvatar(String login, String uploadedFilePath) {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        boolean flag;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UPDATE_AVATAR_BY_LOGIN_SQL);
            statement.setString(1, uploadedFilePath);
            statement.setString(2, login);
            statement.execute();
            flag = true;
        } catch (SQLException e) {
            logger.error(String.format("Cannot avatar throws exception: %s", e));
            flag = false;
        } finally {
            close(statement);
            close(connection);
        }
        return flag;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findEntity(Long id) {
        User user = new User();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SELECT_USER_BY_ID_SQL);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user.setId(id);
                int index = 2;
                String login = resultSet.getString(index++);
                user.setLogin(login);
                String password = resultSet.getString(index++);
                user.setPassword(password);
                String eMail = resultSet.getString(index++);
                user.setEmail(eMail);
                String avatarUrl = resultSet.getString(index++);
                user.setAvatarUrl(avatarUrl);
                String role = resultSet.getString(index++);
                user.setRole(RoleType.valueOf(role.toUpperCase()));
                String status = resultSet.getString(index);
                user.setStatus(StatusType.valueOf(status.toUpperCase()));
            }
        } catch (SQLException e) {
            logger.error(String.format("Cannot get user by login throws exception: %s", e));
        } finally {
            close(statement);
            close(connection);
            close(resultSet);
        }
        return user;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean create(User user) {
        boolean flag = false;
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(INSERT_LOGIN_SQL);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getAvatarUrl());
            statement.setInt(5, user.getRole().getValue());
            statement.setInt(6, user.getStatus().getValue());
            statement.executeUpdate();
            logger.info(String.format("Created account: %s", user));
            flag = true;
        } catch (SQLException e) {
            logger.error(String.format("Cannot insert new user throws exception: %s", e));
        } finally {
            close(statement);
            close(connection);
        }
        return flag;
    }

    @Override
    public User update(User user) {
        return null;
    }
}
