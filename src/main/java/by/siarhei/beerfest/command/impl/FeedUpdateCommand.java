package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.entity.Entity;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.service.FeedUpdateService;
import by.siarhei.beerfest.session.SessionRequestContent;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

public class FeedUpdateCommand implements ActionCommand {

    private static final String JSP_FEED = "path.page.feed";
    private static final String ATTRIBUTE_FEED = "feed";

    @Override
    public String execute(SessionRequestContent content) throws IOException, ServletException {
        String page = ConfigurationManager.getProperty(JSP_FEED);
        List<Entity> list = FeedUpdateService.updateNews();
        content.setAttribute(ATTRIBUTE_FEED,list);
        return page;
    }
}
