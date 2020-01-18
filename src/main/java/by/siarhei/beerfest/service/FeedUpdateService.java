package by.siarhei.beerfest.service;

import by.siarhei.beerfest.entity.Article;
import by.siarhei.beerfest.exception.FeedUpdateException;

import java.util.List;

public interface FeedUpdateService {
    List<Article> updateNews() throws FeedUpdateException;
}
