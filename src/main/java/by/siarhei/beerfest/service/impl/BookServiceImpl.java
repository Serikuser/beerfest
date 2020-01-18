package by.siarhei.beerfest.service.impl;

import by.siarhei.beerfest.dao.BookDao;
import by.siarhei.beerfest.dao.impl.BookDaoImpl;
import by.siarhei.beerfest.entity.Book;
import by.siarhei.beerfest.service.BookService;

import java.sql.Date;


public class BookServiceImpl implements BookService {
    private BookDao dao;

    public BookServiceImpl() {
        dao = new BookDaoImpl();
    }
    @Override
    public boolean checkUserBook(String login) {
        dao = new BookDaoImpl();
        return dao.isUsersBookingFull(login);
    }
    @Override
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
