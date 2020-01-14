package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.entity.Entity;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.service.FeedUpdateService;
import by.siarhei.beerfest.session.SessionRequestContent;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

public class ParticipantListUpdateCommand implements ActionCommand {
    private static final String JSP_PARTICIPANTS = "path.page.participants";
    private static final String ATTRIBUTE_PARTICIPANTS = "participants";

    @Override
    public String execute(SessionRequestContent content) throws IOException, ServletException {
        String page = ConfigurationManager.getProperty(JSP_PARTICIPANTS);
        List<Entity> list = FeedUpdateService.updateParticipants();
        content.setAttribute(ATTRIBUTE_PARTICIPANTS,list);
        return page;
    }
}
