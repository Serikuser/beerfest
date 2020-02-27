package by.siarhei.beerfest.dao;

import by.siarhei.beerfest.entity.impl.Bar;
import by.siarhei.beerfest.exception.DaoException;

import java.util.Map;

/**
 * Interface extends generic {@code BaseDao}
 * represents CRUD methods to access table witch contains {@code Bar} data
 */
public interface BarDao extends BaseDao<Long, Bar> {

    /**
     * Method reads data from table.
     *
     * @param name represents beer name.
     * @return long value of id.
     */
    long findBeerIdByName(String name) throws DaoException;

    /**
     * Method reads data from table.
     *
     * @param name represents food name.
     * @return long value of id.
     */
    long findFoodIdByName(String name) throws DaoException;

    /**
     * Method reads data from table.
     *
     * @param login represents user login.
     * @return boolean values which represents is user have submitted bar .
     */
    boolean isUserSubmittedBar(String login) throws DaoException;

    /**
     * Method reads data from table.
     *
     * @return {@code Map<Long,String>} with pare <id, food name>.
     */
    Map<Long, String> findAllFoodType() throws DaoException;

    /**
     * Method reads data from table.
     *
     * @return {@code Map<Long,String>} with pare <id, beer name>.
     */
    Map<Long, String> findnAllBeerType() throws DaoException;

    /**
     * Method add data to table.
     *
     * @param beerName represents beer name to insert.
     */
    void submitBeer(String beerName) throws DaoException;

    /**
     * Method add data to table.
     *
     * @param foodName represents beer name to insert.
     */
    void submitFood(String foodName) throws DaoException;

    /**
     * Method reads data from table.
     *
     * @param userId represents beer name to insert.
     * @return long values of user's bar id.
     */
    long findBarByUserId(long userId) throws DaoException;
}
