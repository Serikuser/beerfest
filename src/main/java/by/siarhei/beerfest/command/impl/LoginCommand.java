package by.siarhei.beerfest.command.impl;

import static by.siarhei.beerfest.command.Page.Router.*;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.command.Page;
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
    private static final String ATTRIBUTE_DISPLAY_FOR_UNAUTHORIZED = "displayGuest";
    private static final String ATTRIBUTE_DISPLAY_FOR_AUTHORIZED = "displayUser";
    private static final String ATTRIBUTE_STYLE_NONE = "none";
    private static final String ATTRIBUTE_STYLE_EMPTY = "";
    private static final String ATTRIBUTE_BEER_LIST = "beerMap";
    private static final String ATTRIBUTE_FOOD_LIST = "foodMap";
    private static final String ATTRIBUTE_SIGNET_GUEST_VISIBLE = "signetGuestVisible";
    private LanguageService languageService;
    private AccountService accountService;
    private BarService barService;

    public LoginCommand() {
        languageService = new LanguageServiceImpl();
        accountService = new AccountServiceImpl();
        barService = new BarServiceImpl();
    }

    @Override
    public Page execute(SessionRequestContent content) {
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
        return new Page(uri, FORWARD);
    }

    private boolean isEnterDataExist(SessionRequestContent content) {
        return content.getParameter(PARAMETER_USERNAME) != null
                && content.getParameter(PARAMETER_PASSWORD) != null;
    }

    private void fillRequest(SessionRequestContent content, User user, LocaleType localeType) {
        RoleType roleType = user.getRole();
        // TODO: 18.01.2020 remove it to listener
        if (roleType == RoleType.PARTICIPANT) {
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
        content.setAttribute(ATTRIBUTE_USER_LOGIN, user.getLogin());
        content.setAttribute(ATTRIBUTE_USER_AVATAR_URL, user.getAvatarUrl());
        content.setAttribute(ATTRIBUTE_USER_EMAIL, user.getEmail());
        content.setAttribute(ATTRIBUTE_USER_ROLE, user.getRole());
        content.setAttribute(ATTRIBUTE_USER_STATUS, user.getStatus());
    }

    private void fillSession(SessionRequestContent content, User user) {
        content.setSessionAttribute(ATTRIBUTE_ACCOUNT_ID, user.getId());
        content.setSessionAttribute(ATTRIBUTE_USER_LOGIN, user.getLogin());
        content.setSessionAttribute(ATTRIBUTE_USER_AVATAR_URL, user.getAvatarUrl());
        content.setSessionAttribute(ATTRIBUTE_USER_EMAIL, user.getEmail());
        RoleType roleType = user.getRole();
        content.setSessionAttribute(ATTRIBUTE_USER_ROLE, roleType);
        if (roleType == RoleType.GUEST) {
            content.setSessionAttribute(ATTRIBUTE_SIGNET_GUEST_VISIBLE, ATTRIBUTE_STYLE_EMPTY);
        }
        content.setSessionAttribute(ATTRIBUTE_USER_STATUS, user.getStatus());
        content.setSessionAttribute(ATTRIBUTE_DISPLAY_FOR_UNAUTHORIZED, ATTRIBUTE_STYLE_NONE);
        content.setSessionAttribute(ATTRIBUTE_DISPLAY_FOR_AUTHORIZED, ATTRIBUTE_STYLE_EMPTY);
    }
}
