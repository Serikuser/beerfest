package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.BarService;
import by.siarhei.beerfest.session.SessionRequestContent;

import javax.servlet.ServletException;
import java.io.IOException;

public class SubmitBarCommand implements ActionCommand {

    private static final String JSP_MAIN = "path.page.main";
    private static final String ATTRIBUTE_USER_LOGIN = "userLogin";
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String ATTRIBUTE_MESSAGE = "baRErrorMessage";
    private static final String ATTRIBUTE_ACCOUNT_ID = "accountId";
    private static final String SIGNUP_ERROR_JOKE = "ru.message.signup.error.joke";
    private static final String SUBMIT_BAR_ERROR = "ru.message.submit.bar.error";
    private static final String SUBMIT_BAR_SUCCESS = "ru.message.submit.bar.success";
    private static final String PARAMETER_BAR_NAME = "barName";
    private static final String PARAMETER_BEER_TYPE = "beerType";
    private static final String PARAMETER_FOOD_TYPE = "foodType";
    private static final String PARAMETER_BAR_DESCRIPTION = "barDescription";
    private static final String PARAMETER_PLACES = "places";

    @Override
    public String execute(SessionRequestContent content) throws IOException, ServletException {
        String page = ConfigurationManager.getProperty(JSP_MAIN);
        String login = (String) content.getSessionAttribute(ATTRIBUTE_USER_LOGIN);
        if (isEnterDataExist(content) && content.getSessionAttribute(ATTRIBUTE_USER_ROLE) == RoleType.PARTICIPANT) {
            RoleType roleType = (RoleType) content.getSessionAttribute(ATTRIBUTE_USER_ROLE);
            page = ConfigurationManager.getProperty(roleType.getPage());
            if (BarService.checkUserSubmission(login)) {
                String barName = content.getParameter(PARAMETER_BAR_NAME);
                long beerType = Long.parseLong(content.getParameter(PARAMETER_BEER_TYPE));
                long foodType = Long.parseLong(content.getParameter(PARAMETER_FOOD_TYPE));
                String barDescription = content.getParameter(PARAMETER_BAR_DESCRIPTION);
                int places = Integer.parseInt(content.getParameter(PARAMETER_PLACES));
                long accountId = (long) content.getSessionAttribute(ATTRIBUTE_ACCOUNT_ID);
                BarService.submitBar(accountId, barName, beerType, foodType, barDescription, places);
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SUBMIT_BAR_SUCCESS));
            } else {
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SUBMIT_BAR_ERROR));
            }
        } else {
            content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SIGNUP_ERROR_JOKE));
        }
        return page;
    }


    private boolean isEnterDataExist(SessionRequestContent content) {
        return content.getParameter(PARAMETER_BAR_NAME) != null
                && content.getParameter(PARAMETER_BEER_TYPE) != null
                && content.getParameter(PARAMETER_FOOD_TYPE) != null
                && content.getParameter(PARAMETER_BAR_DESCRIPTION) != null;
    }
}
