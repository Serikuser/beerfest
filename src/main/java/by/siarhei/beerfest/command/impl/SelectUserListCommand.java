package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.command.Router;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.entity.impl.User;
import by.siarhei.beerfest.exception.ServiceException;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.AccountService;
import by.siarhei.beerfest.service.LanguageService;
import by.siarhei.beerfest.service.impl.AccountServiceImpl;
import by.siarhei.beerfest.service.impl.LanguageServiceImpl;
import by.siarhei.beerfest.servlet.SessionRequestContent;

import java.util.List;

/**
 * Realization of {@code ActionCommand} interface.
 * Command is processing user's display logic to {@code RoleType} Admin with pagination.
 *
 * using {@code LanguageService}.
 * using {@code BarService}
 */
public class SelectUserListCommand implements ActionCommand {
    private static final String JSP_MAIN = "path.page.main";
    private static final String JSP_ADMIN_USERS = "path.page.admin.users";
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String ATTRIBUTE_INDEX_MESSAGE = "errorMessage";
    private static final String ERROR_MESSAGE_JOKE = "message.signup.error.joke";
    private static final String MESSAGE_SERVER_ERROR = "message.users.error.server";
    private static final String ATTRIBUTE_MESSAGE = "userListMessage";
    private static final String ATTRIBUTE_USER_LIST = "userList";
    private static final String ATTRIBUTE_MAX_VALUE = "maxValue";
    private static final String PARAMETER_PAGE_NUMBER = "pageNumber";

    /**
     * {@code LanguageService} used to display messages based on user's locale.
     */
    private LanguageService languageService;

    /**
     * {@code AccountService} used to process pagination logic and viewing users.
     */
    private AccountService accountService;

    public SelectUserListCommand() {
        languageService = new LanguageServiceImpl();
        accountService = new AccountServiceImpl();
    }

    /**
     * Call method gets pages count from {@code AccountService} and sets it to request,
     * gets called page number fromm request and gets user's list based on it,
     * forwards user to {@code userList.jsp}
     *
     * @param content object that contain request, response and session information.
     * @return {@code Router} with forward routing type.
     */
    @Override
    public Router execute(SessionRequestContent content) {
        String uri = ConfigurationManager.getProperty(JSP_MAIN);
        LocaleType localeType = languageService.defineLocale(content);
        if (content.getSessionAttribute(ATTRIBUTE_USER_ROLE) == RoleType.ADMIN) {
            uri = ConfigurationManager.getProperty(JSP_ADMIN_USERS);
            try {
                int pagesCount = accountService.countPages();
                content.setAttribute(ATTRIBUTE_MAX_VALUE, pagesCount);
                String pageNumber = content.getParameter(PARAMETER_PAGE_NUMBER);
                List<User> list = accountService.findUserList(pageNumber);
                content.setAttribute(ATTRIBUTE_USER_LIST, list);
            } catch (ServiceException e) {
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(MESSAGE_SERVER_ERROR, localeType));
            }
        } else {
            content.setAttribute(ATTRIBUTE_INDEX_MESSAGE, MessageManager.getProperty(ERROR_MESSAGE_JOKE, localeType));
        }
        return new Router(uri);
    }
}
