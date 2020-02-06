package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.command.Router;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.LanguageService;
import by.siarhei.beerfest.service.impl.LanguageServiceImpl;
import by.siarhei.beerfest.servlet.SessionRequestContent;

public class AddNewsCommand implements ActionCommand {
    private static final String JSP_MAIN = "path.page.main";
    private static final String JSP_ADD_NEWS = "path.page.feed.add";
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String ATTRIBUTE_INDEX_MESSAGE = "errorMessage";
    private static final String ERROR_MESSAGE_JOKE = "message.signup.error.joke";

    private LanguageService languageService;

    public AddNewsCommand() {
        languageService = new LanguageServiceImpl();
    }

    @Override
    public Router execute(SessionRequestContent content) {
        String uri = ConfigurationManager.getProperty(JSP_MAIN);
        LocaleType localeType = languageService.defineLocale(content);
        if (content.getSessionAttribute(ATTRIBUTE_USER_ROLE) == RoleType.ADMIN) {
            uri = ConfigurationManager.getProperty(JSP_ADD_NEWS);
        } else {
            content.setAttribute(ATTRIBUTE_INDEX_MESSAGE, MessageManager.getProperty(ERROR_MESSAGE_JOKE, localeType));
        }
        return new Router(uri);
    }
}
