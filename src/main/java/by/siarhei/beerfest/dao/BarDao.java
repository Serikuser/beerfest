package by.siarhei.beerfest.dao;

import by.siarhei.beerfest.entity.Bar;
import by.siarhei.beerfest.exception.FeedUpdateException;

import java.util.Map;

public interface BarDao extends BaseDao<Long, Bar> {

    int findBeerIdByName(String name);

    int findFoodIdByName(String name);

    boolean isUserSubmittedBar(String login);

    Map<Long, String> findAllFoodType() throws FeedUpdateException;

    Map<Long, String> findnAllBeerType() throws FeedUpdateException;

    boolean submitBeer(String beerName);

    boolean submitFood(String beerName);
}
