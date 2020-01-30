package by.siarhei.beerfest.dao.impl;

import by.siarhei.beerfest.dao.DaoTransaction;
import by.siarhei.beerfest.dao.RegistrationDao;
import by.siarhei.beerfest.entity.impl.Registration;
import by.siarhei.beerfest.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDaoImpl extends DaoTransaction implements RegistrationDao {
    private static final String INSERT_REGISTRATION_SQL = "INSERT INTO registration (account_id,token,expired,date) VALUES ('%s','%s','%s','%s')";
    private static final String DELETE_REGISTRATION_BY_ID_SQL = "DELETE FROM registration WHERE registration.id = %s";
    private static final String DELETE_EXPIRED_REGISTRATIONS_SQL = "DELETE FROM registration WHERE expired = true";
    private static final String SELECT_NOT_EXPIRED_TOKENS_ID_SQL = "SELECT registration.id,registration.date FROM registration WHERE expired = false";
    private static final String FIND_REGISTRATION_BY_TOKEN_SQL =
            "SELECT registration.id,registration.account_id,registration.token,registration.expired " +
                    "FROM registration " +
                    "WHERE token=?";
    private static final String UPDATE_EXPIRED_BY_ID_SQL =
            "UPDATE registration " +
                    "SET expired=? " +
                    "WHERE id=?";

    @Override
    public List<Registration> findAll() throws DaoException {
        return null;
    }

    @Override
    public Registration findEntity(Long id) throws DaoException {
        return null;
    }

    @Override
    public void updateExpiredByToken(long id, boolean status) throws DaoException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_EXPIRED_BY_ID_SQL);
            statement.setBoolean(1, status);
            statement.setLong(2, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Cannot change status id: %s", id), e);
        } finally {
            close(statement);
            if (!isInTransaction()) {
                close(connection);
            }
        }
    }

    @Override
    public Registration findRegistrationByToken(String token) throws DaoException {
        Registration registration = new Registration();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(FIND_REGISTRATION_BY_TOKEN_SQL);
            statement.setString(1, token);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int columnIndex = 0;
                long id = resultSet.getLong(++columnIndex);
                registration.setId(id);
                long accountId = resultSet.getLong(++columnIndex);
                registration.setAccountId(accountId);
                ++columnIndex;
                registration.setToken(token);
                boolean isExpired = resultSet.getBoolean(++columnIndex);
                registration.setExpired(isExpired);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Cannot find registration by token: %s", token), e);
        } finally {
            close(statement);
            if (!isInTransaction()) {
                close(connection);
            }
            close(resultSet);
        }
        return registration;
    }

    @Override
    public List<Registration> findAllNotExpiredTokens() throws DaoException {
        List<Registration> list = new ArrayList<>();
        Connection connection = getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_NOT_EXPIRED_TOKENS_ID_SQL);
            while (resultSet.next()) {
                Registration registration = new Registration();
                int columnIndex = 0;
                long id = resultSet.getLong(++columnIndex);
                registration.setId(id);
                Timestamp date = resultSet.getTimestamp(++columnIndex);
                registration.setDate(date);
                list.add(registration);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot select expired tokens throws excpetion", e);
        } finally {
            close(statement);
            if (!isInTransaction()) {
                close(connection);
            }
            close(resultSet);
        }
        return list;
    }

    @Override
    public boolean delete(Registration registration) throws DaoException {
        return false;
    }

    @Override
    public void deleteExpired() throws DaoException {
        Connection connection = getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(DELETE_EXPIRED_REGISTRATIONS_SQL);
        } catch (SQLException e) {
            throw new DaoException("Cannot delete registration", e);
        } finally {
            if (!isInTransaction()) {
                close(connection);
            }
            close(statement);
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        Connection connection = getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(String.format(DELETE_REGISTRATION_BY_ID_SQL, id));
            logger.error(String.format("Token with id: %s deleted", id));
        } catch (SQLException e) {
            throw new DaoException("Cannot delete registration", e);
        } finally {
            if (!isInTransaction()) {
                close(connection);
            }
            close(statement);
        }
    }

    @Override
    public void create(Registration registration) throws DaoException {
        Connection connection = getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            long accountId = registration.getAccountId();
            String token = registration.getToken();
            boolean expired = registration.isExpired();
            Timestamp date = registration.getDate();
            statement.execute(String.format(INSERT_REGISTRATION_SQL, accountId, token, expired, date));
            logger.info(String.format("Created registration:%s", registration));
        } catch (SQLException e) {
            throw new DaoException(String.format("Cannot insert new registration: %s", registration), e);
        } finally {
            close(statement);
            if (!isInTransaction()) {
                close(connection);
            }
        }
    }

    @Override
    public Registration update(Registration registration) throws DaoException {
        return null;
    }
}
