package by.siarhei.beerfest.service.impl;

import by.siarhei.beerfest.dao.BookDao;
import by.siarhei.beerfest.dao.impl.BookDaoImpl;
import by.siarhei.beerfest.entity.Book;
import by.siarhei.beerfest.service.CommandService;

import java.sql.Date;


public class BookService implements CommandService {
    private BookDao dao;

    public BookService() {
        dao = new BookDaoImpl();
    }

    public boolean checkUserBook(String login) {
        dao = new BookDaoImpl();
        return dao.isUsersBookingFull(login);
    }

    public boolean makeBook(long accountId, long barId, int places, Date date) {
        dao = new BookDaoImpl();
        Book book = new Book();
        book.setUserId(accountId);
        book.setBarId(barId);
        book.setPlaces(places);
        book.setDate(date);
        return dao.create(book);
    }
}
