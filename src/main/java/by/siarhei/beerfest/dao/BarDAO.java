package by.siarhei.beerfest.dao;

import by.siarhei.beerfest.entity.Bar;

import java.util.Map;

public interface BarDAO extends BaseDAO<Long, Bar> {

    int findBeerIdByName(String name);

    int findFoodIdByName(String name);

    boolean isUserSubmittedBar(String login);

    Map<Long, String> getFoodList();

    Map<Long, String> getBeerList();

    boolean submitBeer(String beerName);

    boolean submitFood(String beerName);
}
