package by.siarhei.beerfest.service.impl;

import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.service.LanguageService;
import by.siarhei.beerfest.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class LanguageServiceImpl implements LanguageService {
    private static final Logger logger = LogManager.getLogger();

    private static final String ATTRIBUTE_LOCALE = "locale";

    @Override
    public LocaleType defineLocale(SessionRequestContent content) {
        LocaleType localeType;
        if (content.getSessionAttribute(ATTRIBUTE_LOCALE) != null) {
            String locale = content.getSessionAttribute(ATTRIBUTE_LOCALE).toString();
            try {
                localeType = LocaleType.valueOf(locale.toUpperCase());
            } catch (EnumConstantNotPresentException e) {
                logger.error(String.format("Cannot define locale for value %s",locale));
                localeType = LocaleType.RU;
            }
        } else {
            localeType = LocaleType.RU;
        }
        return localeType;
    }


    @Override
    public LocaleType defineLocale(HttpServletRequest request) {
        LocaleType localeType;
        if (request.getSession().getAttribute(ATTRIBUTE_LOCALE) != null) {
            String locale = request.getSession().getAttribute(ATTRIBUTE_LOCALE).toString();
            try {
                localeType = LocaleType.valueOf(locale.toUpperCase());
            } catch (EnumConstantNotPresentException e) {
                logger.error(String.format("Cannot define locale for value %s",locale));
                localeType = LocaleType.RU;
            }
        } else {
            localeType = LocaleType.RU;
        }
        return localeType;
    }
}
