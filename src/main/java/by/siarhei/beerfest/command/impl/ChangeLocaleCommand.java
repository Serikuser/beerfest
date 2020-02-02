package by.siarhei.beerfest.command.impl;

import static by.siarhei.beerfest.command.Page.Router.*;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.command.Page;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.servlet.SessionRequestContent;

public class ChangeLocaleCommand implements ActionCommand {
    private static final String JSP_MAIN = "path.page.index";
    private static final String PARAMETER_LOCALE = "locale";
    private static final String ATTRIBUTE_LOCALE = "locale";

    @Override
    public Page execute(SessionRequestContent content) {
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
        return new Page(uri, REDIRECT);
    }
}
