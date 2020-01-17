package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.entity.Article;
import by.siarhei.beerfest.exception.FeedUpdateException;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.impl.FeedUpdateService;
import by.siarhei.beerfest.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FeedUpdateCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private static final String ATTRIBUTE_ERROR_MESSAGE = "updateErrorMessage";
    private static final String JSP_FEED = "path.page.feed";
    private static final String ATTRIBUTE_FEED = "feed";
    private static final String ERROR_UPDATE_MESSAGE = "ru.message.update.error";


    @Override
    public String execute(SessionRequestContent content) {
        String page = ConfigurationManager.getProperty(JSP_FEED);
        FeedUpdateService service = new FeedUpdateService();
        List<Article> list = new ArrayList<>();
        try {
            list = service.updateNews();
        } catch (FeedUpdateException e) {
            logger.error(String.format("Cant update beer/food list throws exception: %s", e));
            content.setAttribute(ATTRIBUTE_ERROR_MESSAGE, MessageManager.getProperty(ERROR_UPDATE_MESSAGE));
        }
        content.setAttribute(ATTRIBUTE_FEED, list);
        return page;
    }
}
