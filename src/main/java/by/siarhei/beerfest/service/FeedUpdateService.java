package by.siarhei.beerfest.service;

import by.siarhei.beerfest.entity.impl.Article;
import by.siarhei.beerfest.exception.ServiceException;

import java.util.List;

/**
 * Interface represents feed's processing logic
 */
public interface FeedUpdateService {

    /**
     * Method presents the news viewing logic.
     *
     * @return {@code List<Article>} witch contains {@code Article}.
     */
    List<Article> updateNews() throws ServiceException;

    /**
     * Method presents the news viewing logic.
     *
     * @param title represents news title.
     * @param text represents news text.
     * @param uploadedFilePath represents news image path.
     */
    void addNews(String title, String text, String uploadedFilePath) throws ServiceException;

    /**
     * Method presents the news deleting logic.
     */
    void deleteNews(long id) throws ServiceException;
}
