package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.entity.impl.Bar;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MakeBookCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private static final String JSP_MAIN = "path.page.main";
    private static final String JSP_PARTICIPANTS = "path.page.participants";
    private static final String ATTRIBUTE_USER_LOGIN = "userLogin";
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String ATTRIBUTE_MESSAGE = "bookErrorMessage";
    private static final String ATTRIBUTE_INDEX_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_ACCOUNT_ID = "accountId";
    private static final String ATTRIBUTE_PARTICIPANTS = "participants";
    private static final String ATTRIBUTE_BOOK_ERROR_MESSAGE = "bookErrorMessage";
    private static final String ERROR_JOKE = "message.signup.error.joke";
    private static final String ERROR_UPDATE_MESSAGE = "message.update.error";
    private static final String MAKE_BOOK_ERROR = "message.submit.book.error";
    private static final String MAKE_BOOK_ERROR_INVALID_DATE = "message.submit.book.error.date";
    private static final String MAKE_BOOK_ERROR_FULL = "message.submit.book.full";
    private static final String MAKE_BOOK_SUCCESS = "message.submit.book.success";
    private static final String PARAMETER_BAR_ID = "barId";
    private static final String PARAMETER_BOOK_PLACES = "bookPlaces";
    private static final String PARAMETER_BOOK_DATE = "bookDate";
    private LanguageService languageService;
    private BookService bookService;
    private BarService barService;

    public MakeBookCommand() {
        languageService = new LanguageServiceImpl();
        bookService = new BookServiceImpl();
        barService = new BarServiceImpl();
    }

    // FIXME: 14.01.2020 validation
    @Override
    public String execute(SessionRequestContent content) {
        String page = ConfigurationManager.getProperty(JSP_MAIN);
        LocaleType localeType = languageService.defineLocale(content);
        String login = (String) content.getSessionAttribute(ATTRIBUTE_USER_LOGIN);
        if (isEnterDataExist(content) && content.getSessionAttribute(ATTRIBUTE_USER_ROLE) == RoleType.GUEST) {
            page = ConfigurationManager.getProperty(JSP_PARTICIPANTS);
            try {
                if (bookService.checkUserBook(login)) {
                    long accountId = (long) content.getSessionAttribute(ATTRIBUTE_ACCOUNT_ID);
                    long barId = Long.parseLong(content.getParameter(PARAMETER_BAR_ID));
                    int places = Integer.parseInt(content.getParameter(PARAMETER_BOOK_PLACES));
                    String inputDate = content.getParameter(PARAMETER_BOOK_DATE);
                    if (!inputDate.isEmpty()) {
                        Date date = Date.valueOf(inputDate);
                        bookService.makeBook(accountId, barId, places, date);
                        content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(MAKE_BOOK_SUCCESS, localeType));
                    } else {
                        content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(MAKE_BOOK_ERROR_INVALID_DATE, localeType));
                    }
                } else {
                    content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(MAKE_BOOK_ERROR_FULL, localeType));
                }
            } catch (ServiceException e) {
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(MAKE_BOOK_ERROR, localeType));
            }
            // TODO: 17.01.2020 remove it to listener
            List<Bar> list = new ArrayList<>();
            try {
                list = barService.updateParticipants();
            } catch (ServiceException e) {
                logger.error(String.format("Cant update beer/food list throws exception: %s", e));
                content.setAttribute(ATTRIBUTE_BOOK_ERROR_MESSAGE, MessageManager.getProperty(ERROR_UPDATE_MESSAGE, localeType));
            }
            content.setAttribute(ATTRIBUTE_PARTICIPANTS, list);
        } else {
            content.setAttribute(ATTRIBUTE_INDEX_MESSAGE, MessageManager.getProperty(ERROR_JOKE, localeType));
        }

        return page;
    }

    private boolean isEnterDataExist(SessionRequestContent content) {
        return content.getParameter(PARAMETER_BAR_ID) != null
                && content.getParameter(PARAMETER_BOOK_PLACES) != null
                && content.getParameter(PARAMETER_BOOK_DATE) != null;
    }
}
