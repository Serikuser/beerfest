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
 * Command is processing user's profile display logic.
 *
 * using {@code LanguageService}.
 * using {@code BarService}
 */
public class ProfileCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private static final String JSP_MAIN = "path.page.main";
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String ATTRIBUTE_BAR_ERROR_MESSAGE = "baRErrorMessage";
    private static final String ATTRIBUTE_BEER_LIST = "beerMap";
    private static final String ATTRIBUTE_FOOD_LIST = "foodMap";
    private static final String ERROR_UPDATE_MESSAGE = "message.update.error";
    private static final String ERROR_MESSAGE = "message.profile.error";

    /**
     * {@code LanguageService} used to display messages based on user's locale.
     */
    private LanguageService languageService;

    /**
     * {@code BarService} used to define displaying bar data logic for {@code RoleType} Participant.
     */
    private BarService barService;

    public ProfileCommand() {
        languageService = new LanguageServiceImpl();
        barService = new BarServiceImpl();
    }

    /**
     * Call method forwards user to profile.jsp based on {@code RoleType}
     *
     * @param content object that contain request, response and session information.
     * @return {@code Router} with forward routing type.
     */
    @Override
    public Router execute(SessionRequestContent content) {
        String uri = ConfigurationManager.getProperty(JSP_MAIN);
        LocaleType localeType = languageService.defineLocale(content);
        if (content.getSessionAttribute(ATTRIBUTE_USER_ROLE) != RoleType.UNAUTHORIZED) {
            RoleType roleType = (RoleType) content.getSessionAttribute(ATTRIBUTE_USER_ROLE);
            uri = ConfigurationManager.getProperty(roleType.getPage());
            if (roleType == RoleType.PARTICIPANT) {
                fillBeerFoodList(content, localeType);
            }
        } else {
            content.setAttribute(ATTRIBUTE_BAR_ERROR_MESSAGE, MessageManager.getProperty(ERROR_MESSAGE, localeType));
        }
        return new Router(uri);
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
