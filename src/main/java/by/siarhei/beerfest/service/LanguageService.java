package by.siarhei.beerfest.service;

import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.servlet.SessionRequestContent;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface represents defining user's locale logic.
 */
public interface LanguageService {

    /**
     * Method presents the defining user's locale based on {@code SessionRequestContent}.
     *
     * @param content session and request content.
     * @return {@code LocaleType} witch contains user's locale.
     */
    LocaleType defineLocale(SessionRequestContent content);

    /**
     * Method presents the defining user's locale based on {@code HttpServletRequest}.
     *
     * @param request request content.
     * @return {@code LocaleType} witch contains user's locale.
     */
    LocaleType defineLocale(HttpServletRequest request);
}
