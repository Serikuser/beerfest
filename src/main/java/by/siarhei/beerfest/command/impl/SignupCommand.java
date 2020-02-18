package by.siarhei.beerfest.command.impl;


import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.command.Router;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.entity.StatusType;
import by.siarhei.beerfest.entity.impl.User;
import by.siarhei.beerfest.exception.ServiceException;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.AccountService;
import by.siarhei.beerfest.service.LanguageService;
import by.siarhei.beerfest.service.RegistrationService;
import by.siarhei.beerfest.service.impl.AccountServiceImpl;
import by.siarhei.beerfest.service.impl.LanguageServiceImpl;
import by.siarhei.beerfest.service.impl.RegistrationServiceImpl;
import by.siarhei.beerfest.servlet.SessionRequestContent;

public class SignupCommand implements ActionCommand {

    public static final String PARAMETER_USERNAME = "username";
    public static final String PARAMETER_EMAIL = "email";
    public static final String PARAMETER_PASSWORD = "password";
    public static final String PARAMETER_ROLE = "role";
    private static final String JSP_MAIN = "path.page.main";
    private static final String ATTRIBUTE_MESSAGE = "errorMessage";
    private static final String SIGNUP_SUCCESS = "message.signup.success";
    private static final String SIGNUP_ERROR = "message.signup.error";
    private static final String SIGNUP_SERVER_ERROR = "message.signup.error.server";
    private static final String SIGNUP_ERROR_JOKE = "message.signup.error.joke";
    private LanguageService languageService;
    private AccountService accountService;
    private RegistrationService registrationService;

    public SignupCommand(){
        languageService = new LanguageServiceImpl();
        accountService = new AccountServiceImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Override
    public Router execute(SessionRequestContent content) {
        String uri = ConfigurationManager.getProperty(JSP_MAIN);
        LocaleType localeType = languageService.defineLocale(content);
        if (isEnterDataExist(content)) {
            String login = content.getParameter(PARAMETER_USERNAME);
            String eMail = content.getParameter(PARAMETER_EMAIL);
            String password = content.getParameter(PARAMETER_PASSWORD);
            RoleType role = RoleType.valueOf(content.getParameter(PARAMETER_ROLE).toUpperCase());
            try {
                if (accountService.checkUserByLoginEmail(login, eMail)) {
                    accountService.signupUser(login, eMail, password, role, StatusType.INACTIVE);
                    User user = accountService.defineUserByLogin(login);
                    long id = user.getId();
                    registrationService.addRegistrationToken(id,eMail);
                    content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SIGNUP_SUCCESS,localeType));
                } else {
                    content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SIGNUP_ERROR,localeType));
                }
            } catch (ServiceException e) {
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SIGNUP_SERVER_ERROR,localeType));
            }
        } else {
            content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SIGNUP_ERROR_JOKE,localeType));
        }
        return new Router(uri);
    }

    private boolean isEnterDataExist(SessionRequestContent content) {
        return content.getParameter(PARAMETER_USERNAME) != null
                && content.getParameter(PARAMETER_EMAIL) != null
                && content.getParameter(PARAMETER_PASSWORD) != null
                && content.getParameter(PARAMETER_ROLE) != null;
    }
}

