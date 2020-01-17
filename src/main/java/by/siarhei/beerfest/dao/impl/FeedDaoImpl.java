package by.siarhei.beerfest.dao.impl;

import by.siarhei.beerfest.connection.ConnectionPool;
import by.siarhei.beerfest.connection.ProxyConnection;
import by.siarhei.beerfest.dao.FeedDao;
import by.siarhei.beerfest.entity.Article;
import by.siarhei.beerfest.exception.FeedUpdateException;
import by.siarhei.beerfest.factory.ArticleFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FeedDaoImpl implements FeedDao {

    private static final String SQL_SELECT_ALL_FEED =
            "SELECT id, news_title, news_text, news_img_src " +
            "FROM feed";

    @Override
    public List<Article> findAll() throws FeedUpdateException {
        List<Article> list = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ArticleFactory factory = ArticleFactory.getInstance();
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL_FEED);
            while (resultSet.next()) {
                int index = 1;
                int id = resultSet.getInt(index++);
                String title = resultSet.getString(index++);
                String text = resultSet.getString(index++);
                String imgSrc = resultSet.getString(index);
                list.add(factory.create(id, title, text, imgSrc));
            }

        } catch (SQLException e) {
            logger.error(String.format("Feed cant be updated throws exception: %s", e));
            throw new FeedUpdateException(e);
        } finally {
            close(connection);
            close(statement);
            close(resultSet);
        }
        return list;
    }

    @Override
    public Article findEntity(Long id) {
        return null;
    }

    @Override
    public boolean delete(Article article) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean create(Article article) {
        return false;
    }

    @Override
    public Article update(Article article) {
        return null;
    }
}
