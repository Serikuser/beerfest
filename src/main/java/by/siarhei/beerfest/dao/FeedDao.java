package by.siarhei.beerfest.dao;

import by.siarhei.beerfest.entity.impl.Article;

/**
 * Interface extends generic {@code BaseDao}
 * represents CRUD methods to access table witch contains {@code Article} data
 */
public interface FeedDao extends BaseDao<Long, Article> {
}
