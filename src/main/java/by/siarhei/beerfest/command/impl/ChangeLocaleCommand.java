package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.command.Router;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.servlet.SessionRequestContent;

/**
 * Realization of {@code ActionCommand} interface.
 * Command is changing user's locale in session
 */
public class ChangeLocaleCommand implements ActionCommand {
    private static final String JSP_MAIN = "path.page.main";
    private static final String PARAMETER_LOCALE = "locale";
    private static final String ATTRIBUTE_LOCALE = "locale";

    /**
     * Call method is changing user locale and forwards to {@code main.jsp}
     * @param content object that contain request, response and session information.
     * @return {@code Router} with forward routing type.
     */
    @Override
    public Router execute(SessionRequestContent content) {
        String uri = ConfigurationManager.getProperty(JSP_MAIN);
        String locale;
        if (content.getParameter(PARAMETER_LOCALE) != null) {
            locale = content.getParameter(PARAMETER_LOCALE);
            try {
                LocaleType localeType = LocaleType.valueOf(locale.toUpperCase());
                content.setSessionAttribute(ATTRIBUTE_LOCALE, localeType.name());
            } catch (IllegalArgumentException e) {
                content.setSessionAttribute(ATTRIBUTE_LOCALE, LocaleType.RU.name());
            }
        }
        Router router = new Router(uri);
        router.setRedirect();
        return router;
    }
}
