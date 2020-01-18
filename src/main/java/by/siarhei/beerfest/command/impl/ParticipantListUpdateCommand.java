package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.entity.Bar;
import by.siarhei.beerfest.exception.FeedUpdateException;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.impl.BarServiceImpl;
import by.siarhei.beerfest.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ParticipantListUpdateCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private static final String JSP_PARTICIPANTS = "path.page.participants";
    private static final String ATTRIBUTE_PARTICIPANTS = "participants";
    private static final String ERROR_UPDATE_MESSAGE = "ru.message.update.error";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "bookErrorMessage";

    @Override
    public String execute(SessionRequestContent content){
        String page = ConfigurationManager.getProperty(JSP_PARTICIPANTS);
        BarServiceImpl service = new BarServiceImpl();
        List<Bar> list = new ArrayList<>();
        try {
            list = service.updateParticipants();
        } catch (FeedUpdateException e) {
            logger.error(String.format("Cant update beer/food list throws exception: %s", e));
            content.setAttribute(ATTRIBUTE_ERROR_MESSAGE, MessageManager.getProperty(ERROR_UPDATE_MESSAGE));
        }
        content.setAttribute(ATTRIBUTE_PARTICIPANTS,list);
        return page;
    }
}
