package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.command.Router;
import by.siarhei.beerfest.entity.impl.Article;
import by.siarhei.beerfest.exception.ServiceException;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.FeedUpdateService;
import by.siarhei.beerfest.service.LanguageService;
import by.siarhei.beerfest.service.impl.FeedUpdateServiceImpl;
import by.siarhei.beerfest.service.impl.LanguageServiceImpl;
import by.siarhei.beerfest.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Realization of {@code ActionCommand} interface.
 * Command is processing news viewing from database.
 *
 * using {@code LanguageService}.
 * using {@code FeedUpdateService}
 */
public class FeedUpdateCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private static final String ATTRIBUTE_ERROR_MESSAGE = "updateErrorMessage";
    private static final String JSP_FEED = "path.page.feed";
    private static final String ATTRIBUTE_FEED = "feed";
    private static final String ERROR_UPDATE_MESSAGE = "message.update.error";

    /**
     * {@code LanguageService} used to display messages based on user's locale.
     */
    private LanguageService languageService;

    /**
     * {@code FeedUpdateService} used to define news display logic.
     */
    private FeedUpdateService feedUpdateService;

    public FeedUpdateCommand(){
        languageService = new LanguageServiceImpl();
        feedUpdateService = new FeedUpdateServiceImpl();
    }

    /**
     * Call method getting news feed from database and forwarding user to {@code feed.jsp}
     *
     * @param content object that contain request, response and session information.
     * @return {@code Router} with forward routing type.
     */
    @Override
    public Router execute(SessionRequestContent content) {
        String uri = ConfigurationManager.getProperty(JSP_FEED);
        LocaleType localeType = languageService.defineLocale(content);
        List<Article> list = new ArrayList<>();
        try {
            list = feedUpdateService.updateNews();
        } catch (ServiceException e) {
            logger.error(String.format("Cant update beer/food list throws exception: %s", e));
            content.setAttribute(ATTRIBUTE_ERROR_MESSAGE, MessageManager.getProperty(ERROR_UPDATE_MESSAGE,localeType));
        }
        content.setAttribute(ATTRIBUTE_FEED, list);
        return new Router(uri);
    }
}
