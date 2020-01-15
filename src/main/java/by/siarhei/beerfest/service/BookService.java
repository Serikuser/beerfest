package by.siarhei.beerfest.service;

import by.siarhei.beerfest.dao.impl.BookDAOImpl;
import by.siarhei.beerfest.entity.Book;

import java.sql.Date;


public class BookService {
    private BookService() {
    }

    public static boolean checkUserBook(String login) {
        BookDAOImpl dao = new BookDAOImpl();
        return dao.isUsersBookingFull(login);
    }

    public static boolean makeBook(long accountId, long barId, int places, Date date) {
        BookDAOImpl dao = new BookDAOImpl();
        Book book = new Book();
        book.setUserId(accountId);
        book.setBarId(barId);
        book.setPlaces(places);
        book.setDate(date);
        return dao.create(book);
    }
}
