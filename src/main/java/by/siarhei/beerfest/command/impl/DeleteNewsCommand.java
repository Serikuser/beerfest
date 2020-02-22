package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.command.Router;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.entity.impl.Article;
import by.siarhei.beerfest.exception.ServiceException;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.FeedUpdateService;
import by.siarhei.beerfest.service.LanguageService;
import by.siarhei.beerfest.service.impl.FeedUpdateServiceImpl;
import by.siarhei.beerfest.service.impl.LanguageServiceImpl;
import by.siarhei.beerfest.servlet.SessionRequestContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Realization of {@code ActionCommand} interface.
 * Command is processing news delete logic.
 *
 * using {@code LanguageService}.
 * using {@code FeedUpdateService}
 */
public class DeleteNewsCommand implements ActionCommand {
    private static final String JSP_MAIN = "path.page.main";
    private static final String JSP_FEED = "path.page.feed";
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String ATTRIBUTE_FEED = "feed";
    private static final String ATTRIBUTE_INDEX_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_NEWS_ID = "feedId";
    private static final String ATTRIBUTE_NEWS_ERROR_MESSAGE = "updateErrorMessage";
    private static final String ERROR_MESSAGE_JOKE = "message.signup.error.joke";
    private static final String ERROR_MESSAGE_SERVER = "message.news.server.delete.error";

    /**
     * {@code LanguageService} used to display messages based on user's locale.
     */
    private LanguageService languageService;

    /**
     * {@code FeedUpdateService} used to define news display logic.
     */
    private FeedUpdateService feedUpdateService;

    public DeleteNewsCommand(){
        languageService = new LanguageServiceImpl();
        feedUpdateService = new FeedUpdateServiceImpl();
    }

    /**
     * Call method checking user role token from {@code Session Request Content}
     * and deletes news based on newsId taken from request.
     *
     * @param content object that contain request, response and session information.
     * @return {@code Router} with forward routing type.
     */
    @Override
    public Router execute(SessionRequestContent content) {
        String uri = ConfigurationManager.getProperty(JSP_MAIN);
        LocaleType localeType = languageService.defineLocale(content);
        List<Article> list = new ArrayList<>();
        if (content.getSessionAttribute(ATTRIBUTE_USER_ROLE) == RoleType.ADMIN) {
            uri = ConfigurationManager.getProperty(JSP_FEED);
            try{
                long id = Long.parseLong(content.getParameter(ATTRIBUTE_NEWS_ID));
                feedUpdateService.deleteNews(id);
                list = feedUpdateService.updateNews();
            }
            catch (ServiceException e){
                content.setAttribute(ATTRIBUTE_NEWS_ERROR_MESSAGE,MessageManager.getProperty(ERROR_MESSAGE_SERVER,localeType));
            }
        } else {
            content.setAttribute(ATTRIBUTE_INDEX_MESSAGE, MessageManager.getProperty(ERROR_MESSAGE_JOKE, localeType));
        }
        content.setAttribute(ATTRIBUTE_FEED, list);
        return new Router(uri);
    }
}
