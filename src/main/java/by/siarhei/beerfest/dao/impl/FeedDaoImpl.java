package by.siarhei.beerfest.dao.impl;

import by.siarhei.beerfest.dao.DaoTransaction;
import by.siarhei.beerfest.dao.FeedDao;
import by.siarhei.beerfest.entity.impl.Article;
import by.siarhei.beerfest.entity.impl.provider.ArticleProvider;
import by.siarhei.beerfest.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedDaoImpl extends DaoTransaction implements FeedDao {
    private static final String INSERT_NEWS_SQL = "INSERT INTO feed (news_title, news_text, news_img_src) VALUES (?,?,?)";
    private static final String DELETE_NEW_BY_ID_SQL = "DELETE FROM feed WHERE feed.id = %s";
    private static final String SQL_SELECT_ALL_FEED =
            "SELECT id, news_title, news_text, news_img_src " +
                    "FROM feed";

    @Override
    public List<Article> findAll() throws DaoException {
        List<Article> list = new ArrayList<>();
        Connection connection = getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        ArticleProvider factory = ArticleProvider.getInstance();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL_FEED);
            while (resultSet.next()) {
                int index = 0;
                int id = resultSet.getInt(++index);
                String title = resultSet.getString(++index);
                String text = resultSet.getString(++index);
                String imgSrc = resultSet.getString(++index);
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
    public void delete(Long id) throws DaoException {
        Connection connection = getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(String.format(DELETE_NEW_BY_ID_SQL, id));
        } catch (SQLException e) {
            throw new DaoException("Cannot delete news", e);
        } finally {
            if (!isInTransaction()) {
                close(connection);
            }
            close(statement);
        }
    }

    @Override
    public void create(Article article) throws DaoException {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_NEWS_SQL);
            int index = 0;
            statement.setString(++index, article.getTitle());
            statement.setString(++index, article.getText());
            statement.setString(++index, article.getImgSrc());
            statement.execute();
            logger.info(String.format("Created news: %s", article));
        } catch (SQLException e) {
            throw new DaoException(String.format("Cannot insert new news: %s", article), e);
        } finally {
            close(statement);
            if (!isInTransaction()) {
                close(connection);
            }
        }
    }

    @Override
    public Article update(Article article) {
        return null;
    }
}
