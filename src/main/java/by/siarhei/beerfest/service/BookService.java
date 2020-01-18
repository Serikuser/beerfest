package by.siarhei.beerfest.service;

import java.sql.Date;

public interface BookService {
    boolean checkUserBook(String login);

    boolean makeBook(long accountId, long barId, int places, Date date);
}
