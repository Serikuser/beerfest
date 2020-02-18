package by.siarhei.beerfest.dao.impl;

import by.siarhei.beerfest.dao.DaoTransaction;
import by.siarhei.beerfest.dao.UserDao;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.entity.StatusType;
import by.siarhei.beerfest.entity.impl.User;
import by.siarhei.beerfest.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends DaoTransaction implements UserDao {

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
    private static final String UPDATE_STATUS_BY_LOGIN_SQL =
            "UPDATE account " +
                    "SET status=? " +
                    "WHERE login=?";
    private static final String UPDATE_STATUS_BY_ID_SQL =
            "UPDATE account " +
                    "SET status=? " +
                    "WHERE id=?";
    private static final String SELECT_USER_BY_ID_SQL =
            "SELECT account.id,login,password,email,avatar_url,role.name,status.name " +
                    "FROM account " +
                    "INNER JOIN role " +
                    "ON account.role = role.id " +
                    "INNER JOIN status " +
                    "ON account.status = status.id WHERE account.id=?";
    private static final String SELECT_ALL_USER_SQL =
            "SELECT account.id,login,email,role.name,status.name " +
                    "FROM account  " +
                    "INNER JOIN role " +
                    "ON account.role = role.id " +
                    "INNER JOIN status " +
                    "ON account.status = status.id " +
                    "ORDER BY role.id,account.login " +
                    "OFFSET ? " +
                    "LIMIT 5";
    private static final String COUNT_USER_SQL = "SELECT count(*) as count FROM account";

    public UserDaoImpl() {
    }

    @Override
    public User findUserByLogin(String login) throws DaoException {
        User user = new User();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SELECT_USER_BY_LOGIN_SQL);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int columnIndex = 0;
                int id = resultSet.getInt(++columnIndex);
                user.setId(id);
                String userLogin = resultSet.getString(++columnIndex);
                user.setLogin(userLogin);
                String password = resultSet.getString(++columnIndex);
                user.setPassword(password);
                String eMail = resultSet.getString(++columnIndex);
                user.setEmail(eMail);
                String avatarUrl = resultSet.getString(++columnIndex);
                user.setAvatarUrl(avatarUrl);
                String role = resultSet.getString(++columnIndex);
                user.setRole(RoleType.valueOf(role.toUpperCase()));
                String status = resultSet.getString(++columnIndex);
                user.setStatus(StatusType.valueOf(status.toUpperCase()));
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Cannot find user by login: %s", login), e);
        } finally {
            close(statement);
            if (!inTransaction) {
                close(connection);
            }
            close(resultSet);
        }
        return user;
    }

    @Override
    public boolean isLoginPasswordMatch(String login, String password) throws DaoException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean flag = false;
        try {
            statement = connection.prepareStatement(CHECK_USER_BY_LOGIN_PASSWORD_SQL);
            int columnIndex = 0;
            statement.setString(++columnIndex, login);
            statement.setString(++columnIndex, password);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flag = resultSet.getInt(COINCIDENCES_RESULT_INDEX) != 0;
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Cannot check login/password exists: %s", login), e);
        } finally {
            close(statement);
            if (!inTransaction) {
                close(connection);
            }
            close(resultSet);
        }
        return flag;
    }

    @Override
    public boolean isExist(String login, String email) throws DaoException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean flag = false;
        try {
            statement = connection.prepareStatement(CHECK_USER_BY_LOGIN_EMAIL_SQL);
            int index = 0;
            statement.setString(++index, login);
            statement.setString(++index, email);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flag = resultSet.getInt(COINCIDENCES_RESULT_INDEX) != 0;
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Cannot check login/eMail exists exists: %s", login), e);
        } finally {
            close(statement);
            if (!inTransaction) {
                close(connection);
            }
            close(resultSet);
        }
        return flag;
    }

    @Override
    public void updatePassword(String login, String newPassword) throws DaoException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_PASSWORD_BY_LOGIN_SQL);
            statement.setString(1, newPassword);
            statement.setString(2, login);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Cannot change password: %s", login), e);
        } finally {
            close(statement);
            if (!inTransaction) {
                close(connection);
            }
        }
    }

    @Override
    public void updateAvatar(String login, String uploadedFilePath) throws DaoException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_AVATAR_BY_LOGIN_SQL);
            statement.setString(1, uploadedFilePath);
            statement.setString(2, login);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Cannot change avatar: %s", login), e);
        } finally {
            close(statement);
            if (!inTransaction) {
                close(connection);
            }
        }
    }

    @Override
    public void updateStatus(String login, int status) throws DaoException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_STATUS_BY_LOGIN_SQL);
            statement.setInt(1, status);
            statement.setString(2, login);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Cannot change status login: %s", login), e);
        } finally {
            close(statement);
            if (!inTransaction) {
                close(connection);
            }
        }
    }

    @Override
    public void updateStatusById(long id, int status) throws DaoException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_STATUS_BY_ID_SQL);
            statement.setInt(1, status);
            statement.setLong(2, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Cannot change status id: %s", id), e);
        } finally {
            close(statement);
            if (!inTransaction) {
                close(connection);
            }
        }
    }

    @Override
    public int countUsers() throws DaoException {
        int count = 0;
        Connection connection = getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(COUNT_USER_SQL);
            while (resultSet.next()) {
                count = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot count users", e);
        } finally {
            close(statement);
            close(resultSet);
            if (!inTransaction) {
                close(connection);
            }
        }
        return count;
    }

    @Override
    public List<User> findAll(long offset) throws DaoException {
        List<User> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SELECT_ALL_USER_SQL);
            statement.setLong(1, offset);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                int index = 0;
                long id = resultSet.getLong(++index);
                user.setId(id);
                String login = resultSet.getString(++index);
                user.setLogin(login);
                String eMail = resultSet.getString(++index);
                user.setEmail(eMail);
                String role = resultSet.getString(++index);
                user.setRole(RoleType.valueOf(role.toUpperCase()));
                String status = resultSet.getString(++index);
                user.setStatus(StatusType.valueOf(status.toUpperCase()));
                list.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot find users", e);
        } finally {
            close(statement);
            if (!inTransaction) {
                close(connection);
            }
            close(resultSet);
        }
        return list;
    }

    @Override
    public List<User> findAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public User findEntity(Long id) throws DaoException {
        User user = new User();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SELECT_USER_BY_ID_SQL);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user.setId(id);
                int index = 1;
                String login = resultSet.getString(++index);
                user.setLogin(login);
                String password = resultSet.getString(++index);
                user.setPassword(password);
                String eMail = resultSet.getString(++index);
                user.setEmail(eMail);
                String avatarUrl = resultSet.getString(++index);
                user.setAvatarUrl(avatarUrl);
                String role = resultSet.getString(++index);
                user.setRole(RoleType.valueOf(role.toUpperCase()));
                String status = resultSet.getString(++index);
                user.setStatus(StatusType.valueOf(status.toUpperCase()));
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Cannot find user by id: %s", id), e);
        } finally {
            close(statement);
            if (!inTransaction) {
                close(connection);
            }
            close(resultSet);
        }
        return user;
    }

    @Override
    public boolean delete(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public void create(User user) throws DaoException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_LOGIN_SQL);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getAvatarUrl());
            statement.setInt(5, user.getRole().getValue());
            statement.setInt(6, user.getStatus().getValue());
            statement.executeUpdate();
            logger.info(String.format("Created account: %s", user));
        } catch (SQLException e) {
            throw new DaoException(String.format("Cannot insert new user: %s", user), e);
        } finally {
            close(statement);
            if (!inTransaction) {
                close(connection);
            }
        }
    }

    @Override
    public User update(User user) {
        throw new UnsupportedOperationException();
    }
}
