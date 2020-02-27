package by.siarhei.beerfest.dao;

import by.siarhei.beerfest.entity.Entity;
import by.siarhei.beerfest.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Generic Interface realisation of Data Access Object pattern
 * represents CRUD methods to data base access and three default methods
 * uses {@code Logger} to grand access to logger to all realisations and default methods
 */

public interface BaseDao<K, T extends Entity> {
    Logger logger = LogManager.getLogger();

    /**
     * Method reads data from table.
     *
     * @return {@code List} which contains all entities from table.
     */
    List<T> findAll() throws DaoException;

    /**
     * Method reads data from table.
     *
     * @param id represents primary key from table.
     * @return {@code Entity} .
     */
    T findEntity(K id) throws DaoException;

    /**
     * Method removes data from table.
     *
     * @param t represents entity to delete from table.
     * @return boolean values indicating a success of operation .
     */
    boolean delete(T t) throws DaoException;

    /**
     * Method removes data from table.
     *
     * @param id represents primary key from table.
     */
    void delete(K id) throws DaoException;

    /**
     * Method adds data to table.
     *
     * @param t represents entity to add in table.
     */
    void create(T t) throws DaoException;

    /**
     * Method updates data in table.
     *
     * @param t represents entity to update in table.
     * @return {@code Entity} old version of entity .
     */
    T update(T t) throws DaoException;

    /**
     * The default method that implements the basic logic for statements closing
     *
     * @param statement represents statement witch need to be closed
     */
    default void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error(String.format("Statement cant be closed throws exception: %s", e));
        }
    }

    /**
     * The default method that implements the basic logic for resultSets closing
     *
     * @param resultSet represents ResultSet witch need to be closed
     */
    default void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.error(String.format("ResultSet cant be closed throws exception: %s", e));
        }
    }

    /**
     * The default method that implements the basic logic for connection closing
     *
     * @param connection represents Connection witch need to be closed
     */
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

