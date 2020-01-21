package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.exception.ServiceException;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.BarService;
import by.siarhei.beerfest.service.LanguageService;
import by.siarhei.beerfest.service.impl.BarServiceImpl;
import by.siarhei.beerfest.service.impl.LanguageServiceImpl;
import by.siarhei.beerfest.servlet.SessionRequestContent;

public class SubmitBeerCommand implements ActionCommand {
    private static final String JSP_MAIN = "path.page.main";
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String ATTRIBUTE_MESSAGE = "beerSubmitMessage";
    private static final String PARAMETER_BEER_NAME = "beerName";
    private static final String SUBMIT_BEER_SUCCESS = "message.submit.beer.success";
    private static final String SUBMIT_BEER_ERROR = "message.submit.beer.error";
    private static final String SIGNUP_ERROR_JOKE = "message.signup.error.joke";
    private LanguageService languageService;
    private BarService barService;

    public SubmitBeerCommand(){
        languageService = new LanguageServiceImpl();
        barService = new BarServiceImpl();
    }

    @Override
    public String execute(SessionRequestContent content) {
        String page = ConfigurationManager.getProperty(JSP_MAIN);
        LocaleType localeType = languageService.defineLocale(content);
        if (content.getSessionAttribute(ATTRIBUTE_USER_ROLE) == RoleType.ADMIN) {
            RoleType roleType = (RoleType) content.getSessionAttribute(ATTRIBUTE_USER_ROLE);
            page = ConfigurationManager.getProperty(roleType.getPage());
            String beerName = content.getParameter(PARAMETER_BEER_NAME);
            try {
                barService.submitBeer(beerName);
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SUBMIT_BEER_SUCCESS,localeType));
            } catch (ServiceException e) {
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SUBMIT_BEER_ERROR,localeType));
            }
        } else {
            content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SIGNUP_ERROR_JOKE,localeType));
        }
        return page;
    }
}
