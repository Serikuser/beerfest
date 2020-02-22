package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.command.Router;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.exception.ServiceException;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.AccountService;
import by.siarhei.beerfest.service.LanguageService;
import by.siarhei.beerfest.service.impl.AccountServiceImpl;
import by.siarhei.beerfest.service.impl.LanguageServiceImpl;
import by.siarhei.beerfest.servlet.SessionRequestContent;

/**
 * Realization of {@code ActionCommand} interface.
 * Command is making password changes logic for authorized users
 * based on users's login and eMail from session and new password from request in MD5 encryption.
 * It is understood that the password is already encoded.
 *
 * using {@code LanguageService}.
 * using {@code AccountService}
 */
public class ChangePasswordCommand implements ActionCommand {
    private static final String JSP_MAIN = "path.page.main";
    private static final String PARAMETER_NEW_PASSWORD = "newPassword";
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String ATTRIBUTE_USER_LOGIN = "userLogin";
    private static final String ATTRIBUTE_USER_EMAIL = "userEmail";
    private static final String ATTRIBUTE_MESSAGE = "errorMessage";
    private static final String SUCCESS_MESSAGE = "message.change.password.success";
    private static final String ERROR_MESSAGE = "message.change.password.error";

    /**
     * {@code LanguageService} used to display messages based on user's locale.
     */
    private LanguageService languageService;

    /**
     * {@code AccountService} used to define account logic.
     */
    private AccountService accountService;

    public ChangePasswordCommand(){
        languageService = new LanguageServiceImpl();
        accountService = new AccountServiceImpl();

    }

    /**
     * Call method checking role type from {@code Session Request Content}
     * and changes user's password in database password cakes already encrypted.
     * forwards user to profile.jsp based on {@code RoleType}
     *
     * @param content object that contain request, response and session information.
     * @return {@code Router} with forward routing type.
     */
    @Override
    public Router execute(SessionRequestContent content) {
        String uri = ConfigurationManager.getProperty(JSP_MAIN);
        LocaleType localeType = languageService.defineLocale(content);
        if (content.getSessionAttribute(ATTRIBUTE_USER_ROLE) != RoleType.UNAUTHORIZED) {
            String newPassword = content.getParameter(PARAMETER_NEW_PASSWORD);
            String login = content.getSessionAttribute(ATTRIBUTE_USER_LOGIN).toString();
            String eMail = content.getSessionAttribute(ATTRIBUTE_USER_EMAIL).toString();
            RoleType roleType = (RoleType) content.getSessionAttribute(ATTRIBUTE_USER_ROLE);
            uri = ConfigurationManager.getProperty(roleType.getPage());
            try {
                accountService.changeUserPassword(login, eMail, newPassword);
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SUCCESS_MESSAGE,localeType));
            } catch (ServiceException e) {
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(ERROR_MESSAGE,localeType));
            }
        }
        return new Router(uri);
    }
}
