package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.command.Router;
import by.siarhei.beerfest.entity.impl.Bar;
import by.siarhei.beerfest.exception.ServiceException;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.BarService;
import by.siarhei.beerfest.service.LanguageService;
import by.siarhei.beerfest.service.impl.BarServiceImpl;
import by.siarhei.beerfest.service.impl.LanguageServiceImpl;
import by.siarhei.beerfest.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ParticipantListUpdateCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private static final String JSP_PARTICIPANTS = "path.page.participants";
    private static final String ATTRIBUTE_PARTICIPANTS = "participants";
    private static final String ERROR_UPDATE_MESSAGE = "message.update.error";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "bookErrorMessage";
    private LanguageService languageService;
    private BarService barService;

    public ParticipantListUpdateCommand(){
        languageService = new LanguageServiceImpl();
        barService = new BarServiceImpl();
    }

    @Override
    public Router execute(SessionRequestContent content){
        String uri = ConfigurationManager.getProperty(JSP_PARTICIPANTS);
        LocaleType localeType = languageService.defineLocale(content);
        List<Bar> list = new ArrayList<>();
        try {
            list = barService.updateParticipants();
        } catch (ServiceException e) {
            logger.error(String.format("Cant update beer/food list throws exception: %s", e));
            content.setAttribute(ATTRIBUTE_ERROR_MESSAGE, MessageManager.getProperty(ERROR_UPDATE_MESSAGE,localeType));
        }
        content.setAttribute(ATTRIBUTE_PARTICIPANTS,list);
        return new Router(uri);
    }
}
