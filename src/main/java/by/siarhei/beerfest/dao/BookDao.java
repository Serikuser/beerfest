package by.siarhei.beerfest.dao;

import by.siarhei.beerfest.entity.Book;
import by.siarhei.beerfest.exception.DaoException;

import java.util.List;

public interface BookDao extends BaseDao<Long, Book> {

    List<Book> findUserBook(Long id) throws DaoException;

    boolean isUsersBookingFull(String login) throws DaoException;
}
