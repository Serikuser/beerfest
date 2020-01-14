package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.entity.Entity;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.BookService;
import by.siarhei.beerfest.service.FeedUpdateService;
import by.siarhei.beerfest.session.SessionRequestContent;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

public class MakeBookCommand implements ActionCommand {
    private static final String JSP_MAIN = "path.page.main";
    private static final String JSP_PARTICIPANTS = "path.page.participants";
    private static final String ATTRIBUTE_USER_LOGIN = "userLogin";
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String ATTRIBUTE_MESSAGE = "bookErrorMessage";
    private static final String ATTRIBUTE_ACCOUNT_ID = "accountId";
    private static final String ATTRIBUTE_PARTICIPANTS = "participants";
    private static final String ERROR_JOKE = "ru.message.signup.error.joke";
    private static final String MAKE_BOOK_ERROR = "ru.message.submit.book.error";
    private static final String MAKE_BOOK_SUCCESS = "ru.message.submit.book.success";
    private static final String PARAMETER_BAR_ID = "barId";
    private static final String PARAMETER_BOOK_PLACES = "bookPlaces";
    private static final String PARAMETER_BOOK_DATE = "bookDate";


    // FIXME: 14.01.2020
    @Override
    public String execute(SessionRequestContent content) throws IOException, ServletException {
        String page = ConfigurationManager.getProperty(JSP_MAIN);
        String login = (String) content.getSessionAttribute(ATTRIBUTE_USER_LOGIN);
        if (isEnterDataExist(content) && content.getSessionAttribute(ATTRIBUTE_USER_ROLE) == RoleType.GUEST) {
            page = ConfigurationManager.getProperty(JSP_PARTICIPANTS);
            if (BookService.checkUserBook(login)) {
                long accountId = (long) content.getSessionAttribute(ATTRIBUTE_ACCOUNT_ID);
                long barId = Long.parseLong(content.getParameter(PARAMETER_BAR_ID));
                int places = Integer.parseInt(content.getParameter(PARAMETER_BOOK_PLACES));
                String date = content.getParameter(PARAMETER_BOOK_DATE);
                BookService.makeBook(accountId,barId,places,date);
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(MAKE_BOOK_SUCCESS));
            } else {
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(MAKE_BOOK_ERROR));
            }
        } else {
            content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(ERROR_JOKE));
        }
        List<Entity> list = FeedUpdateService.updateParticipants();
        content.setAttribute(ATTRIBUTE_PARTICIPANTS,list);
        return page;
    }


    private boolean isEnterDataExist(SessionRequestContent content) {
        return content.getParameter(PARAMETER_BAR_ID) != null
                && content.getParameter(PARAMETER_BOOK_PLACES) != null
                && content.getParameter(PARAMETER_BOOK_DATE) != null;
    }
}
