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

public class ProfileCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private static final String JSP_MAIN = "path.page.main";
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String ATTRIBUTE_BAR_ERROR_MESSAGE = "baRErrorMessage";
    private static final String ATTRIBUTE_BEER_LIST = "beerMap";
    private static final String ATTRIBUTE_FOOD_LIST = "foodMap";
    private static final String ERROR_UPDATE_MESSAGE = "ru.message.update.error";
    private static final String ERROR_MESSAGE = "ru.message.profile.error";

    @Override
    public String execute(SessionRequestContent content){
        String page = ConfigurationManager.getProperty(JSP_MAIN);
        BarService service = new BarService();
        if (content.getSessionAttribute(ATTRIBUTE_USER_ROLE) != RoleType.UNAUTHORIZED) {
            RoleType roleType = (RoleType) content.getSessionAttribute(ATTRIBUTE_USER_ROLE);
            page = ConfigurationManager.getProperty(roleType.getPage());
            if (roleType == RoleType.PARTICIPANT) {
                Map<Long, String> beerList = new HashMap<>();
                Map<Long, String> foodList = new HashMap<>();
                try {
                    beerList = service.updateBeerList();
                    foodList = service.updateFoodList();
                } catch (FeedUpdateException e) {
                    logger.error(String.format("Cant update beer/food list throws exception: %s", e));
                    content.setAttribute(ATTRIBUTE_BAR_ERROR_MESSAGE, MessageManager.getProperty(ERROR_UPDATE_MESSAGE));
                }
                content.setAttribute(ATTRIBUTE_BEER_LIST, beerList);
                content.setAttribute(ATTRIBUTE_FOOD_LIST, foodList);
            }
        } else {
            content.setAttribute(ATTRIBUTE_BAR_ERROR_MESSAGE, MessageManager.getProperty(ERROR_MESSAGE));
        }
        return page;
    }
}
