package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.servlet.SessionRequestContent;

public class ChangeLocaleCommand implements ActionCommand {
    private static final String JSP_MAIN = "path.page.main";
    private static final String PARAMETER_LOCALE = "locale";
    private static final String ATTRIBUTE_LOCALE = "locale";

    @Override
    public String execute(SessionRequestContent content) {
        String page = ConfigurationManager.getProperty(JSP_MAIN);
        String locale;
        if (content.getParameter(PARAMETER_LOCALE) != null) {
            locale = content.getParameter(PARAMETER_LOCALE);
            content.setSessionAttribute(ATTRIBUTE_LOCALE, locale);
        }
        return page;
    }
}
