package by.siarhei.beerfest.dao;

import by.siarhei.beerfest.entity.impl.Book;
import by.siarhei.beerfest.exception.DaoException;

import java.util.List;

/**
 * Interface extends generic {@code BaseDao}
 * represents CRUD methods to access table witch contains {@code Book} data
 */
public interface BookDao extends BaseDao<Long, Book> {

    /**
     * Method reads data from table.
     *
     * @param id represents user's id.
     * @return {@code List<Book>} witch contains all user's {@code Book} .
     */
    List<Book> findUserBook(Long id) throws DaoException;

    /**
     * Method reads data from table.
     *
     * @param id represents bar's id.
     * @return {@code List<Book>} witch contains all bar's {@code Book} .
     */
    List<Book> findBarBook(Long id) throws DaoException;

    /**
     * Method reads data from table.
     *
     * @param login represents user login.
     * @return boolean values which represents is user have book limit.
     */
    boolean isUsersBookingFull(String login) throws DaoException;
}
