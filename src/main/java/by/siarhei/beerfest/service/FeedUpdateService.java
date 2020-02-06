package by.siarhei.beerfest.service;

import by.siarhei.beerfest.entity.impl.Article;
import by.siarhei.beerfest.exception.ServiceException;

import java.util.List;

public interface FeedUpdateService {
    List<Article> updateNews() throws ServiceException;

    void addNews(String title, String text, String uploadedFilePath) throws ServiceException;

    void deleteNews(long id) throws ServiceException;
}
