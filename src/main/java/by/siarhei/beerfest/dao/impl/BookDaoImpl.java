package by.siarhei.beerfest.dao.impl;

import by.siarhei.beerfest.connection.ConnectionPool;
import by.siarhei.beerfest.connection.ProxyConnection;
import by.siarhei.beerfest.dao.BookDao;
import by.siarhei.beerfest.entity.Book;
import by.siarhei.beerfest.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookDaoImpl implements BookDao {
    private static final String INSERT_BOOK_SQL = "INSERT INTO book (guest_id,bar_id,reserved_places,reservation_date) VALUES (?,?,?,?)";
    private static final String COINCIDENCES_RESULT_INDEX = "coincidences";
    private static final String CHECK_BOOK_BY_LOGIN_SQL = String.format(
            "SELECT count(*) as %s " +
            "FROM book " +
            "INNER JOIN account " +
            "ON book.guest_id = account.id " +
            "WHERE account.login= ?", COINCIDENCES_RESULT_INDEX);

    @Override
    public List<Book> findAll() {
        return null;
    }

    @Override
    public Book findEntity(Long id) {
        return null;
    }

    @Override
    public boolean delete(Book book) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public void create(Book book) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(INSERT_BOOK_SQL);
            statement.setLong(1, book.getUserId());
            statement.setLong(2, book.getBarId());
            statement.setInt(3, book.getPlaces());
            statement.setDate(4, book.getDate());
            statement.executeUpdate();
            logger.info(String.format("Created book: %s", book));
        } catch (SQLException e) {
            throw new DaoException(String.format("Cannot insert new book: %s", book), e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public boolean isUsersBookingFull(String login) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean flag = false;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CHECK_BOOK_BY_LOGIN_SQL);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flag = resultSet.getInt(COINCIDENCES_RESULT_INDEX) < 2;
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Cannot check users book exists: %s", login), e);
        } finally {
            close(statement);
            close(connection);
            close(resultSet);
        }
        return flag;
    }
}
