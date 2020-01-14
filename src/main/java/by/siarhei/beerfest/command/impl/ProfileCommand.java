package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.FeedUpdateService;
import by.siarhei.beerfest.session.SessionRequestContent;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

public class ProfileCommand implements ActionCommand {
    private static final String JSP_MAIN = "path.page.main";
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_BEER_LIST = "beerMap";
    private static final String ATTRIBUTE_FOOD_LIST = "foodMap";
    private static final String ERROR_MESSAGE = "ru.message.profile.error";

    @Override
    public String execute(SessionRequestContent content) throws IOException, ServletException {
        String page = ConfigurationManager.getProperty(JSP_MAIN);
        if (content.getSessionAttribute(ATTRIBUTE_USER_ROLE) != RoleType.UNAUTHORIZED) {
            RoleType roleType = (RoleType) content.getSessionAttribute(ATTRIBUTE_USER_ROLE);
            page = ConfigurationManager.getProperty(roleType.getPage());
            if(roleType==RoleType.PARTICIPANT){
                Map<Long, String> beerList = FeedUpdateService.updateBeerList();
                content.setAttribute(ATTRIBUTE_BEER_LIST,beerList);
                Map<Long, String> foodList = FeedUpdateService.updateFoodList();
                content.setAttribute(ATTRIBUTE_FOOD_LIST,foodList);
            }
        } else {
            content.setAttribute(ATTRIBUTE_ERROR_MESSAGE, MessageManager.getProperty(ERROR_MESSAGE));
        }
        return page;
    }
}
