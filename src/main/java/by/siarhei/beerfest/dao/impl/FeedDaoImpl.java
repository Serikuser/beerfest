package by.siarhei.beerfest.dao.impl;

import by.siarhei.beerfest.dao.DaoTransaction;
import by.siarhei.beerfest.dao.FeedDao;
import by.siarhei.beerfest.entity.impl.Article;
import by.siarhei.beerfest.exception.DaoException;
import by.siarhei.beerfest.factory.ArticleFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FeedDaoImpl extends DaoTransaction implements FeedDao {

    private static final String SQL_SELECT_ALL_FEED =
            "SELECT id, news_title, news_text, news_img_src " +
                    "FROM feed";

    @Override
    public List<Article> findAll() throws DaoException {
        List<Article> list = new ArrayList<>();
        Connection connection = getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        ArticleFactory factory = ArticleFactory.getInstance();
        try {
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
            throw new DaoException("Cant update new list", e);
        } finally {
            if (!isInTransaction()) {
                close(connection);
            }
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
    public void delete(Long id) {
    }

    @Override
    public void create(Article article) {

    }

    @Override
    public Article update(Article article) {
        return null;
    }
}
