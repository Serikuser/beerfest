package by.siarhei.beerfest.service;

import by.siarhei.beerfest.exception.ServiceException;

import java.sql.Date;

public interface BookService {
    boolean checkUserBook(String login) throws ServiceException;

    void makeBook(long accountId, long barId, int places, Date date) throws ServiceException;
}
