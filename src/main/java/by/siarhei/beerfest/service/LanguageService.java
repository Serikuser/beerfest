package by.siarhei.beerfest.service;

import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.servlet.SessionRequestContent;

import javax.servlet.http.HttpServletRequest;

public interface LanguageService {
    LocaleType defineLocale(SessionRequestContent content);

    LocaleType defineLocale(HttpServletRequest request);
}
