package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.command.Router;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.entity.impl.Book;
import by.siarhei.beerfest.exception.ServiceException;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.BarService;
import by.siarhei.beerfest.service.BookService;
import by.siarhei.beerfest.service.LanguageService;
import by.siarhei.beerfest.service.impl.BarServiceImpl;
import by.siarhei.beerfest.service.impl.BookServiceImpl;
import by.siarhei.beerfest.service.impl.LanguageServiceImpl;
import by.siarhei.beerfest.servlet.SessionRequestContent;

import java.util.List;

/**
 * Realization of {@code ActionCommand} interface.
 * Command is processing user booking logic.
 *
 * using {@code LanguageService}.
 * using {@code BookService}
 * using {@code BarService}
 */
public class UserBookCommand implements ActionCommand {
    private static final String JSP_MAIN = "path.page.main";
    private static final String JSP_GUEST_BOOK = "path.page.guest.book";
    private static final String JSP_PARTICIPANT_BOOK = "path.page.participant.book";
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String ATTRIBUTE_INDEX_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_ACCOUNT_ID = "accountId";
    private static final String ATTRIBUTE_BOOK_ERROR_MESSAGE = "bookErrorMessage";
    private static final String ATTRIBUTE_BOOK = "bookList";
    private static final String ERROR_MESSAGE_JOKE = "message.signup.error.joke";
    private static final String ERROR_MESSAGE_EMPTY = "message.book.empty.error";
    private static final String ERROR_MESSAGE_SERVER = "message.book.server.error";

    /**
     * {@code LanguageService} used to display messages based on user's locale.
     */
    private LanguageService languageService;

    /**
     * {@code BookService} used to define booking logic.
     */
    private BookService bookService;

    /**
     * {@code BarService} used to define users bar booked logic.
     */
    private BarService barService;

    public UserBookCommand() {
        languageService = new LanguageServiceImpl();
        bookService = new BookServiceImpl();
        barService = new BarServiceImpl();
    }

    /**
     * Call method makes book display logic based on {@code RoleType}
     * forwards {@code RoleType} Guest: to {@code /guest/book.jsp}
     * forwards {@code RoleType} Participant: to {@code /participant/book.jsp}
     *
     * @param content object that contain request, response and session information.
     * @return {@code Router} with forward routing type.
     */
    @Override
    public Router execute(SessionRequestContent content) {
        String uri = ConfigurationManager.getProperty(JSP_MAIN);
        LocaleType localeType = languageService.defineLocale(content);
        if (content.getSessionAttribute(ATTRIBUTE_USER_ROLE) != RoleType.UNAUTHORIZED) {
            RoleType roleType = RoleType.valueOf(content.getSessionAttribute(ATTRIBUTE_USER_ROLE).toString().toUpperCase());
            switch (roleType) {
                case GUEST:
                    uri = ConfigurationManager.getProperty(JSP_GUEST_BOOK);
                    findGuestBook(content, localeType);
                    break;
                case PARTICIPANT:
                    uri = ConfigurationManager.getProperty(JSP_PARTICIPANT_BOOK);
                    findParticipantBook(content, localeType);
                    break;
            }
        } else {
            content.setAttribute(ATTRIBUTE_INDEX_MESSAGE, MessageManager.getProperty(ERROR_MESSAGE_JOKE, localeType));
        }
        return new Router(uri);
    }

    /**
     * Call method makes book display logic for {@code RoleType} Guest
     *
     * @param content object that contain request, response and session information.
     * @param localeType object that contains property file path based on user's locale.
     */
    private void findGuestBook(SessionRequestContent content, LocaleType localeType) {
        try {
            long id = Long.parseLong(content.getSessionAttribute(ATTRIBUTE_ACCOUNT_ID).toString());
            List<Book> list = bookService.findUserBook(id);
            if (!list.isEmpty()) {
                content.setAttribute(ATTRIBUTE_BOOK, list);
            } else {
                content.setAttribute(ATTRIBUTE_BOOK_ERROR_MESSAGE, MessageManager.getProperty(ERROR_MESSAGE_EMPTY, localeType));
            }
        } catch (ServiceException e) {
            content.setAttribute(ATTRIBUTE_BOOK_ERROR_MESSAGE, MessageManager.getProperty(ERROR_MESSAGE_SERVER, localeType));
        }
    }

    /**
     * Call method makes book display logic for {@code RoleType} Participant
     *
     * @param content object that contain request, response and session information.
     * @param localeType object that contains property file path based on user's locale.
     */
    private void findParticipantBook(SessionRequestContent content, LocaleType localeType) {
        try {
            long userId = Long.parseLong(content.getSessionAttribute(ATTRIBUTE_ACCOUNT_ID).toString());
            long barId = barService.findUserByBarId(userId);
            List<Book> list = bookService.finBarBook(barId);
            if (!list.isEmpty()) {
                content.setAttribute(ATTRIBUTE_BOOK, list);
            } else {
                content.setAttribute(ATTRIBUTE_BOOK_ERROR_MESSAGE, MessageManager.getProperty(ERROR_MESSAGE_EMPTY, localeType));
            }
        } catch (ServiceException e) {
            content.setAttribute(ATTRIBUTE_BOOK_ERROR_MESSAGE, MessageManager.getProperty(ERROR_MESSAGE_SERVER, localeType));
        }
    }
}
