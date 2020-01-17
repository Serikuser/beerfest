package by.siarhei.beerfest.service.impl;

import by.siarhei.beerfest.dao.FeedDao;
import by.siarhei.beerfest.dao.impl.FeedDaoImpl;
import by.siarhei.beerfest.entity.Article;
import by.siarhei.beerfest.exception.FeedUpdateException;
import by.siarhei.beerfest.service.CommandService;

import java.util.List;

public class FeedUpdateService implements CommandService {
    private FeedDao dao;

    public FeedUpdateService() {
        dao = new FeedDaoImpl();
    }

    public List<Article> updateNews() throws FeedUpdateException {
        return dao.findAll();
    }

}
