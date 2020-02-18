package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.command.Router;
import by.siarhei.beerfest.exception.ServiceException;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.LanguageService;
import by.siarhei.beerfest.service.RegistrationService;
import by.siarhei.beerfest.service.impl.LanguageServiceImpl;
import by.siarhei.beerfest.service.impl.RegistrationServiceImpl;
import by.siarhei.beerfest.servlet.SessionRequestContent;

public class ContinueRegistrationCommand implements ActionCommand {
    private static final String JSP_MAIN = "path.page.main";
    private static final String ATTRIBUTE_MESSAGE = "errorMessage";
    private static final String ERROR_MESSAGE_COMPLETE_REGISTRATION = "message.change.registration.token.error";
    private static final String SERVER_ERROR_MESSAGE_COMPLETE_REGISTRATION = "message.change.registration.server.error";
    private static final String SUCCESS_MESSAGE_COMPLETE_REGISTRATION = "message.change.registration.token.success";
    private static final String PARAMETER_TOKEN = "token";
    private LanguageService languageService;
    private RegistrationService registrationService;

    public ContinueRegistrationCommand() {
        languageService = new LanguageServiceImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Override
    public Router execute(SessionRequestContent content) {
        String uri = ConfigurationManager.getProperty(JSP_MAIN);
        LocaleType localeType = languageService.defineLocale(content);
        if (content.getParameter(PARAMETER_TOKEN) != null) {
            String token = content.getParameter(PARAMETER_TOKEN);
            try {
                if (registrationService.completeRegistration(token)) {
                    content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SUCCESS_MESSAGE_COMPLETE_REGISTRATION, localeType));
                } else {
                    content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(ERROR_MESSAGE_COMPLETE_REGISTRATION, localeType));
                }
            } catch (ServiceException e) {
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SERVER_ERROR_MESSAGE_COMPLETE_REGISTRATION, localeType));
            }
        }
        return new Router(uri);
    }
}
