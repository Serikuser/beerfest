package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.exception.FeedUpdateException;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.impl.BarService;
import by.siarhei.beerfest.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

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
    private static final String SIGNUP_ERROR_JOKE = "ru.message.signup.error.joke";
    private static final String SUBMIT_BAR_ERROR = "ru.message.submit.bar.error";
    private static final String SUBMIT_BAR_SUCCESS = "ru.message.submit.bar.success";
    private static final String PARAMETER_BAR_NAME = "barName";
    private static final String PARAMETER_BEER_TYPE = "beerType";
    private static final String PARAMETER_FOOD_TYPE = "foodType";
    private static final String PARAMETER_BAR_DESCRIPTION = "barDescription";
    private static final String PARAMETER_PLACES = "places";
    private static final String ERROR_UPDATE_MESSAGE = "ru.message.update.error";


    @Override
    public String execute(SessionRequestContent content) {
        String page = ConfigurationManager.getProperty(JSP_MAIN);
        BarService service = new BarService();
        String login = (String) content.getSessionAttribute(ATTRIBUTE_USER_LOGIN);
        if (isEnterDataExist(content) && content.getSessionAttribute(ATTRIBUTE_USER_ROLE) == RoleType.PARTICIPANT) {
            RoleType roleType = (RoleType) content.getSessionAttribute(ATTRIBUTE_USER_ROLE);
            page = ConfigurationManager.getProperty(roleType.getPage());
            if (service.checkUserSubmission(login)) {
                String barName = content.getParameter(PARAMETER_BAR_NAME);
                long beerType = Long.parseLong(content.getParameter(PARAMETER_BEER_TYPE));
                long foodType = Long.parseLong(content.getParameter(PARAMETER_FOOD_TYPE));
                String barDescription = content.getParameter(PARAMETER_BAR_DESCRIPTION);
                int places = Integer.parseInt(content.getParameter(PARAMETER_PLACES));
                long accountId = (long) content.getSessionAttribute(ATTRIBUTE_ACCOUNT_ID);
                service.submitBar(accountId, barName, beerType, foodType, barDescription, places);
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SUBMIT_BAR_SUCCESS));
            } else {
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SUBMIT_BAR_ERROR));
            }
        } else {
            content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SIGNUP_ERROR_JOKE));
        }
        // TODO: 17.01.2020 remove it to session listener
        BarService barService = new BarService();
        Map<Long, String> beerList = new HashMap<>();
        Map<Long, String> foodList = new HashMap<>();
        try {
            beerList = barService.updateBeerList();
            foodList = barService.updateFoodList();
        } catch (FeedUpdateException e) {
            logger.error(String.format("Cant update beer/food list throws exception: %s", e));
            content.setAttribute(ATTRIBUTE_BAR_ERROR_MESSAGE, MessageManager.getProperty(ERROR_UPDATE_MESSAGE));
        }
        content.setAttribute(ATTRIBUTE_BEER_LIST, beerList);
        content.setAttribute(ATTRIBUTE_FOOD_LIST, foodList);
        return page;
    }


    private boolean isEnterDataExist(SessionRequestContent content) {
        return content.getParameter(PARAMETER_BAR_NAME) != null
                && content.getParameter(PARAMETER_BEER_TYPE) != null
                && content.getParameter(PARAMETER_FOOD_TYPE) != null
                && content.getParameter(PARAMETER_BAR_DESCRIPTION) != null;
    }
}
