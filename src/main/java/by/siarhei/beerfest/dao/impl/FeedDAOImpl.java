package by.siarhei.beerfest.dao.impl;

import by.siarhei.beerfest.connection.ConnectionPool;
import by.siarhei.beerfest.connection.ProxyConnection;
import by.siarhei.beerfest.dao.FeedDAO;
import by.siarhei.beerfest.entity.Entity;
import by.siarhei.beerfest.factory.ArticleFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FeedDAOImpl implements FeedDAO {

    private static final String SQL_SELECT_ALL_FEED =
            "SELECT id, news_title, news_text, news_img_src " +
            "FROM feed";

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public Entity findEntity(Object id) {
        return null;
    }

    @Override
    public boolean delete(Entity entity) {
        return false;
    }

    @Override
    public boolean delete(Object id) {
        return false;
    }

    @Override
    public boolean create(Entity entity) {
        return false;
    }

    @Override
    public Entity update(Entity entity) {
        return null;
    }

    public List<Entity> getFeedList() {
        List<Entity> list = new ArrayList<>();
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
        } finally {
            close(connection);
            close(statement);
            close(resultSet);
        }
        return list;
    }
}
