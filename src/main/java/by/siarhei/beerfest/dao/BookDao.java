package by.siarhei.beerfest.dao;

import by.siarhei.beerfest.entity.Book;

public interface BookDao extends BaseDao<Long, Book> {

    boolean isUsersBookingFull(String login);
}
