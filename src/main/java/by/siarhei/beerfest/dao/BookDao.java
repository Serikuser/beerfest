package by.siarhei.beerfest.dao;

import by.siarhei.beerfest.entity.Book;
import by.siarhei.beerfest.exception.DaoException;

public interface BookDao extends BaseDao<Long, Book> {

    boolean isUsersBookingFull(String login) throws DaoException;
}
