package by.siarhei.beerfest.dao;

import by.siarhei.beerfest.entity.Book;

public interface BookDAO extends BaseDAO <Long, Book> {

    boolean isUsersBookingFull(String login);
}
