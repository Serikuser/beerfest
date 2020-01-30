package by.siarhei.beerfest.service;

import by.siarhei.beerfest.entity.impl.Book;
import by.siarhei.beerfest.exception.ServiceException;

import java.sql.Date;
import java.util.List;

public interface BookService {
    boolean checkUserBook(String login) throws ServiceException;

    void makeBook(long accountId, long barId, int places, Date date) throws ServiceException;

    List<Book> findUserBook(Long id) throws ServiceException;

    List<Book> finBarBook(Long id) throws ServiceException;

    void deleteBook(long bookId) throws ServiceException;
}
