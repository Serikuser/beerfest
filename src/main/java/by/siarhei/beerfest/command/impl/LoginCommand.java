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
import by.siarhei.beerfest.service.BarService;
import by.siarhei.beerfest.service.LanguageService;
import by.siarhei.beerfest.service.impl.AccountServiceImpl;
import by.siarhei.beerfest.service.impl.BarServiceImpl;
import by.siarhei.beerfest.service.impl.LanguageServiceImpl;
import by.siarhei.beerfest.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Realization of {@code ActionCommand} interface.
 * Command is processing login user logic.
 *
 * using {@code LanguageService}.
 * using {@code AccountService}
 * using {@code BarService}
 */
public class LoginCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private static final String JSP_MAIN = "path.page.main";
    private static final String PARAMETER_USERNAME = "username";
    private static final String PARAMETER_PASSWORD = "password";
    private static final String ATTRIBUTE_ACCOUNT_ID = "accountId";
    private static final String ATTRIBUTE_USER_LOGIN = "userLogin";
    private static final String ATTRIBUTE_USER_AVATAR_URL = "userAvatarUrl";
    private static final String ATTRIBUTE_USER_EMAIL = "userEmail";
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String ATTRIBUTE_USER_STATUS = "userStatus";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_BAR_ERROR_MESSAGE = "baRErrorMessage";
    private static final String ERROR_MESSAGE = "message.login.error";
    private static final String ERROR_CONFIRM_MESSAGE = "message.login.confirm.error";
    private static final String ERROR_BANNED_MESSAGE = "message.login.banned.error";
    private static final String ERROR_SERVER_MESSAGE = "message.login.error.server";
    private static final String ERROR_UPDATE_MESSAGE = "message.update.error";
    private static final String SIGNUP_ERROR_JOKE = "message.signup.error.joke";
    private static final String ATTRIBUTE_BEER_LIST = "beerMap";
    private static final String ATTRIBUTE_FOOD_LIST = "foodMap";

    /**
     * {@code LanguageService} used to display messages based on user's locale.
     */
    private LanguageService languageService;

    /**
     * {@code AccountService} used to define login logic.
     */
    private AccountService accountService;

    /**
     * {@code BarService} used to define displaying bar data logic for {@code RoleType} Participant.
     */
    private BarService barService;

    public LoginCommand() {
        languageService = new LanguageServiceImpl();
        accountService = new AccountServiceImpl();
        barService = new BarServiceImpl();
    }

    /**
     * Call method checking login data, and defines login logic based on {@code StatusType}
     * all {@code StatusType} Active, forwarding on profile.jsp based on {@code RoleType}
     * and get seession filled by needed values, special feed logic to {@code RoleType} Participant
     * by adding bar data to request.
     * all not {@code StatusType} Active got forwarding to {@code main.jsp} with specified error message.
     *
     * @param content object that contain request, response and session information.
     * @return {@code Router} with forward routing type.
     */
    @Override
    public Router execute(SessionRequestContent content) {
        String uri = ConfigurationManager.getProperty(JSP_MAIN);
        LocaleType localeType = languageService.defineLocale(content);
        if (isEnterDataExist(content)) {
            String login = content.getParameter(PARAMETER_USERNAME);
            String password = content.getParameter(PARAMETER_PASSWORD);
            try {
                if (accountService.checkUserByLoginPassword(login, password)) {
                    User user = accountService.defineUserByLogin(login);
                    StatusType statusType = user.getStatus();
                    switch (statusType) {
                        case ACTIVE:
                            fillSession(content, user);
                            fillRequest(content, user, localeType);
                            RoleType roleType = (RoleType) content.getAttribute(ATTRIBUTE_USER_ROLE);
                            uri = ConfigurationManager.getProperty(roleType.getPage());
                            logger.info(String.format("User: %s has logged in", user));
                            break;
                        case BANNED:
                            content.setAttribute(ATTRIBUTE_ERROR_MESSAGE, MessageManager.getProperty(ERROR_BANNED_MESSAGE, localeType));
                            break;
                        case INACTIVE:
                            content.setAttribute(ATTRIBUTE_ERROR_MESSAGE, MessageManager.getProperty(ERROR_CONFIRM_MESSAGE, localeType));
                            break;
                    }
                } else {
                    content.setAttribute(ATTRIBUTE_ERROR_MESSAGE, MessageManager.getProperty(ERROR_MESSAGE, localeType));
                }
            } catch (ServiceException e) {
                content.setAttribute(ATTRIBUTE_ERROR_MESSAGE, MessageManager.getProperty(ERROR_SERVER_MESSAGE, localeType));
            }
        } else {
            content.setAttribute(ATTRIBUTE_ERROR_MESSAGE, MessageManager.getProperty(SIGNUP_ERROR_JOKE, localeType));
        }
        return new Router(uri);
    }

    /**
     * Call method check entered data for existence
     *
     * @param content object that contain request, response and session information.
     * @return boolean value is entered data exists.
     */
    private boolean isEnterDataExist(SessionRequestContent content) {
        return content.getParameter(PARAMETER_USERNAME) != null
                && content.getParameter(PARAMETER_PASSWORD) != null;
    }

    /**
     * Call method filled request by needed data with special logic for {@code RoleType} Participant
     *
     * @param content object that contain request, response and session information.
     * @param user object that contains all user information based on data from database.
     * @param localeType object that contains property file path based on user's locale.
     */
    private void fillRequest(SessionRequestContent content, User user, LocaleType localeType) {
        RoleType roleType = user.getRole();
        if (roleType == RoleType.PARTICIPANT) {
            fillBeerFoodList(content, localeType);
        }
        content.setAttribute(ATTRIBUTE_USER_LOGIN, user.getLogin());
        content.setAttribute(ATTRIBUTE_USER_AVATAR_URL, user.getAvatarUrl());
        content.setAttribute(ATTRIBUTE_USER_EMAIL, user.getEmail());
        content.setAttribute(ATTRIBUTE_USER_ROLE, user.getRole());
        content.setAttribute(ATTRIBUTE_USER_STATUS, user.getStatus());
    }

    /**
     * Call method filled session by needed data
     *
     * @param content object that contain request, response and session information.
     * @param user object that contains all user information based on data from database
     */
    private void fillSession(SessionRequestContent content, User user) {
        content.setSessionAttribute(ATTRIBUTE_ACCOUNT_ID, user.getId());
        content.setSessionAttribute(ATTRIBUTE_USER_LOGIN, user.getLogin());
        content.setSessionAttribute(ATTRIBUTE_USER_AVATAR_URL, user.getAvatarUrl());
        content.setSessionAttribute(ATTRIBUTE_USER_EMAIL, user.getEmail());
        RoleType roleType = user.getRole();
        content.setSessionAttribute(ATTRIBUTE_USER_ROLE, roleType);
        content.setSessionAttribute(ATTRIBUTE_USER_STATUS, user.getStatus());
    }

    /**
     * Call method filled request for {@code RoleType} Participant with bar data
     *
     * @param content object that contain request, response and session information.
     * @param localeType object that contains property path based on user's locale
     */
    private void fillBeerFoodList(SessionRequestContent content, LocaleType localeType) {
        Map<Long, String> beerList = new HashMap<>();
        Map<Long, String> foodList = new HashMap<>();
        try {
            beerList = barService.updateBeerList();
            foodList = barService.updateFoodList();
        } catch (ServiceException e) {
            logger.error(String.format("Cant update beer/food list throws exception: %s", e));
            content.setAttribute(ATTRIBUTE_BAR_ERROR_MESSAGE, MessageManager.getProperty(ERROR_UPDATE_MESSAGE, localeType));
        }
        content.setAttribute(ATTRIBUTE_BEER_LIST, beerList);
        content.setAttribute(ATTRIBUTE_FOOD_LIST, foodList);
    }
}
