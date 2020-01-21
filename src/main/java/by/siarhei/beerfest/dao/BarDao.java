package by.siarhei.beerfest.dao;

import by.siarhei.beerfest.entity.Bar;
import by.siarhei.beerfest.exception.DaoException;

import java.util.Map;

public interface BarDao extends BaseDao<Long, Bar> {

    int findBeerIdByName(String name) throws DaoException;

    int findFoodIdByName(String name) throws DaoException;

    boolean isUserSubmittedBar(String login) throws DaoException;

    Map<Long, String> findAllFoodType() throws DaoException;

    Map<Long, String> findnAllBeerType() throws DaoException;

    void submitBeer(String beerName) throws DaoException;

    void submitFood(String beerName) throws DaoException;
}
