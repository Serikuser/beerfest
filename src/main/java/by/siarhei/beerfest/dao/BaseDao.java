package by.siarhei.beerfest.dao;

import by.siarhei.beerfest.entity.Entity;
import by.siarhei.beerfest.exception.FeedUpdateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface BaseDao<K, T extends Entity> {
    Logger logger = LogManager.getLogger();

    List<T> findAll() throws FeedUpdateException;

    T findEntity(K id);

    boolean delete(T t);

    boolean delete(K id);

    boolean create(T t);

    T update(T t);

    default void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error(String.format("Statement cant be closed throws exception: %s", e));
        }
    }

    default void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.error(String.format("ResultSet cant be closed throws exception: %s", e));
        }
    }

    default void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error(String.format("Connection cant be closed throws exception: %s", e));
        }
    }

}

