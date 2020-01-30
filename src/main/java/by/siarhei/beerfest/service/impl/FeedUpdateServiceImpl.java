package by.siarhei.beerfest.service.impl;

import by.siarhei.beerfest.dao.FeedDao;
import by.siarhei.beerfest.dao.impl.FeedDaoImpl;
import by.siarhei.beerfest.entity.impl.Article;
import by.siarhei.beerfest.exception.DaoException;
import by.siarhei.beerfest.exception.ServiceException;
import by.siarhei.beerfest.service.FeedUpdateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class FeedUpdateServiceImpl implements FeedUpdateService {
    private static final Logger logger = LogManager.getLogger();

    private FeedDao dao;

    public FeedUpdateServiceImpl() {
        dao = new FeedDaoImpl();
    }

    @Override
    public List<Article> updateNews() throws ServiceException {
        try {
            return dao.findAll();
        } catch (DaoException e) {
            logger.error("Cannot update news ", e);
            throw new ServiceException(e);
        }
    }

}
