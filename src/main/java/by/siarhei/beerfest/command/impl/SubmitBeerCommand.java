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

/**
 * Realization of {@code ActionCommand} interface.
 * Command is processing adding bar data into database by {@code RoleType} Admin
 *
 * using {@code LanguageService}.
 * using {@code BarService}
 */
public class SubmitBeerCommand implements ActionCommand {
    private static final String JSP_MAIN = "path.page.main";
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String ATTRIBUTE_MESSAGE = "beerSubmitMessage";
    private static final String PARAMETER_BEER_NAME = "beerName";
    private static final String SUBMIT_BEER_SUCCESS = "message.submit.beer.success";
    private static final String SUBMIT_BEER_ERROR = "message.submit.beer.error";
    private static final String SIGNUP_ERROR_JOKE = "message.signup.error.joke";

    /**
     * {@code LanguageService} used to display messages based on user's locale.
     */
    private LanguageService languageService;

    /**
     * {@code BarService} used to define adding bar data logic.
     */
    private BarService barService;

    public SubmitBeerCommand(){
        languageService = new LanguageServiceImpl();
        barService = new BarServiceImpl();
    }

    /**
     * Call method checking {@code RoleType} and process data adding logic in {@code BarService}
     * forwards user to profile.jsp based on {@code RoleType}
     * command has vice validations due to the user's trusted role
     *
     * @param content object that contain request, response and session information.
     * @return {@code Router} with forward routing type.
     */
    @Override
    public Router execute(SessionRequestContent content) {
        String uri = ConfigurationManager.getProperty(JSP_MAIN);
        LocaleType localeType = languageService.defineLocale(content);
        if (content.getSessionAttribute(ATTRIBUTE_USER_ROLE) == RoleType.ADMIN) {
            RoleType roleType = (RoleType) content.getSessionAttribute(ATTRIBUTE_USER_ROLE);
            uri = ConfigurationManager.getProperty(roleType.getPage());
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
        return new Router(uri);
    }
}
