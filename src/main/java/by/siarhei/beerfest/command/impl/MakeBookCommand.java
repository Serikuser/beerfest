package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.entity.Bar;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.exception.FeedUpdateException;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.impl.BarService;
import by.siarhei.beerfest.service.impl.BookService;
import by.siarhei.beerfest.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;
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
    private static final String ATTRIBUTE_ACCOUNT_ID = "accountId";
    private static final String ATTRIBUTE_PARTICIPANTS = "participants";
    private static final String ATTRIBUTE_BOOK_ERROR_MESSAGE = "bookErrorMessage";
    private static final String ERROR_JOKE = "ru.message.signup.error.joke";
    private static final String ERROR_UPDATE_MESSAGE = "ru.message.update.error";
    private static final String MAKE_BOOK_ERROR = "ru.message.submit.book.error";
    private static final String MAKE_BOOK_ERROR_INVALID_DATE = "ru.message.submit.book.error.date";
    private static final String MAKE_BOOK_ERROR_FULL = "ru.message.submit.book.full";
    private static final String MAKE_BOOK_SUCCESS = "ru.message.submit.book.success";
    private static final String PARAMETER_BAR_ID = "barId";
    private static final String PARAMETER_BOOK_PLACES = "bookPlaces";
    private static final String PARAMETER_BOOK_DATE = "bookDate";


    // FIXME: 14.01.2020 validation
    @Override
    public String execute(SessionRequestContent content){
        BookService service = new BookService();
        String page = ConfigurationManager.getProperty(JSP_MAIN);
        String login = (String) content.getSessionAttribute(ATTRIBUTE_USER_LOGIN);
        if (isEnterDataExist(content) && content.getSessionAttribute(ATTRIBUTE_USER_ROLE) == RoleType.GUEST) {
            page = ConfigurationManager.getProperty(JSP_PARTICIPANTS);
            if (service.checkUserBook(login)) {
                long accountId = (long) content.getSessionAttribute(ATTRIBUTE_ACCOUNT_ID);
                long barId = Long.parseLong(content.getParameter(PARAMETER_BAR_ID));
                int places = Integer.parseInt(content.getParameter(PARAMETER_BOOK_PLACES));
                String inputDate = content.getParameter(PARAMETER_BOOK_DATE);
                if (!inputDate.isEmpty()) {
                    Date date = Date.valueOf(inputDate);
                    if (service.makeBook(accountId, barId, places, date)) {
                        content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(MAKE_BOOK_SUCCESS));
                    } else {
                        content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(MAKE_BOOK_ERROR));
                    }
                } else {
                    content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(MAKE_BOOK_ERROR_INVALID_DATE));
                }
            } else {
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(MAKE_BOOK_ERROR_FULL));
            }
        } else {
            content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(ERROR_JOKE));
        }
        // TODO: 17.01.2020 remove it to session listener
        BarService barService = new BarService();
        List<Bar> list = new ArrayList<>();
        try {
            list = barService.updateParticipants();
        } catch (FeedUpdateException e) {
            logger.error(String.format("Cant update beer/food list throws exception: %s", e));
            content.setAttribute(ATTRIBUTE_BOOK_ERROR_MESSAGE, MessageManager.getProperty(ERROR_UPDATE_MESSAGE));
        }
        content.setAttribute(ATTRIBUTE_PARTICIPANTS, list);
        return page;
    }

    private boolean isEnterDataExist(SessionRequestContent content) {
        return content.getParameter(PARAMETER_BAR_ID) != null
                && content.getParameter(PARAMETER_BOOK_PLACES) != null
                && content.getParameter(PARAMETER_BOOK_DATE) != null;
    }
}
