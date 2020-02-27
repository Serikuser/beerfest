package by.siarhei.beerfest.service;

import by.siarhei.beerfest.entity.impl.Book;
import by.siarhei.beerfest.exception.ServiceException;

import java.sql.Date;
import java.util.List;

/**
 * Interface represents book's processing logic
 */
public interface BookService {

    /**
     * Method presents the checking user's books logic.
     *
     * @param login represents user's login.
     * @return boolean value is user already have max books.
     */
    boolean checkUserBook(String login) throws ServiceException;

    /**
     * Method presents booking logic.
     *
     * @param accountId represents user's id.
     * @param barId represents bar's id.
     * @param places represents number of booked places.
     * @param date represents book date.
     */
    void makeBook(long accountId, long barId, int places, Date date) throws ServiceException;

    /**
     * Method presents user's books viewing logic.
     *
     * @param id represents user's id.
     * @return {@code List<Book>} witch contains user's {@code Book}
     */
    List<Book> findUserBook(Long id) throws ServiceException;

    /**
     * Method presents user's books viewing logic.
     *
     * @param id represents bar's id.
     * @return {@code List<Book>} witch contains bar's {@code Book}
     */
    List<Book> finBarBook(Long id) throws ServiceException;

    /**
     * Method presents book deleting logic.
     *
     * @param bookId represents book's id.
     */
    void deleteBook(long bookId) throws ServiceException;
}
