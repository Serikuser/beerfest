package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.impl.BarServiceImpl;
import by.siarhei.beerfest.servlet.SessionRequestContent;

public class SubmitBeerCommand implements ActionCommand {
    private static final String JSP_MAIN = "path.page.main";
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String ATTRIBUTE_MESSAGE = "beerSubmitMessage";
    private static final String PARAMETER_BEER_NAME = "beerName";
    private static final String SUBMIT_BEER_SUCCESS = "ru.message.submit.beer.success";
    private static final String SUBMIT_BEER_ERROR = "ru.message.submit.beer.error";
    private static final String SIGNUP_ERROR_JOKE = "ru.message.signup.error.joke";

    @Override
    public String execute(SessionRequestContent content){
        String page = ConfigurationManager.getProperty(JSP_MAIN);
        BarServiceImpl service = new BarServiceImpl();
        if (content.getSessionAttribute(ATTRIBUTE_USER_ROLE) == RoleType.ADMIN) {
            RoleType roleType = (RoleType) content.getSessionAttribute(ATTRIBUTE_USER_ROLE);
            page = ConfigurationManager.getProperty(roleType.getPage());
            String beerName = content.getParameter(PARAMETER_BEER_NAME);
            if (service.submitBeer(beerName)) {
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SUBMIT_BEER_SUCCESS));
            } else {
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SUBMIT_BEER_ERROR));
            }
        } else {
            content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SIGNUP_ERROR_JOKE));
        }
        return page;
    }
}
