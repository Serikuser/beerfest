package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.command.Router;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.exception.ServiceException;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.BarService;
import by.siarhei.beerfest.service.LanguageService;
import by.siarhei.beerfest.service.impl.BarServiceImpl;
import by.siarhei.beerfest.service.impl.LanguageServiceImpl;
import by.siarhei.beerfest.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Realization of {@code ActionCommand} interface.
 * Command is processing bar submission logic for {@code RoleType} Participant.
 *
 * using {@code LanguageService}.
 * using {@code BarService}
 */
public class SubmitBarCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private static final String JSP_MAIN = "path.page.main";
    private static final String ATTRIBUTE_USER_LOGIN = "userLogin";
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String ATTRIBUTE_MESSAGE = "baRErrorMessage";
    private static final String ATTRIBUTE_ACCOUNT_ID = "accountId";
    private static final String ATTRIBUTE_BAR_ERROR_MESSAGE = "baRErrorMessage";
    private static final String ATTRIBUTE_BEER_LIST = "beerMap";
    private static final String ATTRIBUTE_FOOD_LIST = "foodMap";
    private static final String SIGNUP_ERROR_JOKE = "message.signup.error.joke";
    private static final String SUBMIT_BAR_ERROR = "message.submit.bar.error";
    private static final String SUBMIT_BAR_ERROR_DATA = "message.submit.bar.data.error";
    private static final String SUBMIT_BAR_SERVER_ERROR = "message.submit.bar.error.server";
    private static final String SUBMIT_BAR_SUCCESS = "message.submit.bar.success";
    private static final String PARAMETER_BAR_NAME = "barName";
    private static final String PARAMETER_BEER_TYPE = "beerType";
    private static final String PARAMETER_FOOD_TYPE = "foodType";
    private static final String PARAMETER_BAR_DESCRIPTION = "barDescription";
    private static final String PARAMETER_PLACES = "places";
    private static final String ERROR_UPDATE_MESSAGE = "message.update.error";

    /**
     * {@code LanguageService} used to display messages based on user's locale.
     */
    private LanguageService languageService;

    /**
     * {@code BarService} used to define bar data logic.
     */
    private BarService barService;

    public SubmitBarCommand() {
        languageService = new LanguageServiceImpl();
        barService = new BarServiceImpl();
    }

    /**
     * Call method validate bar data in {@code BarService}
     * and forwarding user to profile.jsp based on {@code RoleType}
     *
     * @param content object that contain request, response and session information.
     * @return {@code Router} with forward routing type.
     */
    @Override
    public Router execute(SessionRequestContent content) {
        String uri = ConfigurationManager.getProperty(JSP_MAIN);
        LocaleType localeType = languageService.defineLocale(content);
        String login = (String) content.getSessionAttribute(ATTRIBUTE_USER_LOGIN);
        if (isEnterDataExist(content) && content.getSessionAttribute(ATTRIBUTE_USER_ROLE) == RoleType.PARTICIPANT) {
            RoleType roleType = (RoleType) content.getSessionAttribute(ATTRIBUTE_USER_ROLE);
            uri = ConfigurationManager.getProperty(roleType.getPage());
            try {
                if (barService.checkUserSubmission(login)) {
                    String places = content.getParameter(PARAMETER_PLACES);
                    String barName = content.getParameter(PARAMETER_BAR_NAME);
                    long beerType = Long.parseLong(content.getParameter(PARAMETER_BEER_TYPE));
                    long foodType = Long.parseLong(content.getParameter(PARAMETER_FOOD_TYPE));
                    String barDescription = content.getParameter(PARAMETER_BAR_DESCRIPTION);
                    long accountId = (long) content.getSessionAttribute(ATTRIBUTE_ACCOUNT_ID);
                    if (barService.submitBar(accountId, barName, beerType, foodType, barDescription, places)) {
                        content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SUBMIT_BAR_SUCCESS, localeType));
                    } else {
                        content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SUBMIT_BAR_ERROR_DATA, localeType));
                    }
                } else {
                    content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SUBMIT_BAR_ERROR, localeType));
                }
            } catch (ServiceException e) {
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SUBMIT_BAR_SERVER_ERROR, localeType));
            }
        } else {
            content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SIGNUP_ERROR_JOKE, localeType));
        }
        fillBeerFoodList(content, localeType);
        return new Router(uri);
    }

    /**
     * Call method check entered data for existence
     *
     * @param content object that contain request, response and session information.
     * @return boolean value is entered data exists.
     */
    private boolean isEnterDataExist(SessionRequestContent content) {
        return content.getParameter(PARAMETER_BAR_NAME) != null
                && content.getParameter(PARAMETER_PLACES) != null
                && content.getParameter(PARAMETER_BEER_TYPE) != null
                && content.getParameter(PARAMETER_FOOD_TYPE) != null
                && content.getParameter(PARAMETER_BAR_DESCRIPTION) != null;
    }

    /**
     * Call method filled request by beef/food data after forward to profile.jsp
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
