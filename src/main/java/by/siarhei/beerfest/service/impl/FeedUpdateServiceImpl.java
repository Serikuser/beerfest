package by.siarhei.beerfest.service.impl;

import by.siarhei.beerfest.dao.FeedDao;
import by.siarhei.beerfest.dao.impl.FeedDaoImpl;
import by.siarhei.beerfest.entity.Article;
import by.siarhei.beerfest.exception.FeedUpdateException;
import by.siarhei.beerfest.service.FeedUpdateService;

import java.util.List;

public class FeedUpdateServiceImpl implements FeedUpdateService {
    private FeedDao dao;

    public FeedUpdateServiceImpl() {
        dao = new FeedDaoImpl();
    }
    @Override
    public List<Article> updateNews() throws FeedUpdateException {
        return dao.findAll();
    }

}
