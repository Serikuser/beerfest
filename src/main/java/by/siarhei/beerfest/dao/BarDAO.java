package by.siarhei.beerfest.dao;

import by.siarhei.beerfest.entity.Bar;
import by.siarhei.beerfest.entity.Entity;

import java.util.List;
import java.util.Map;

public interface BarDAO extends BaseDAO<Long, Bar> {

    int getBeerIdByName(String name);

    int getFoodIdByName(String name);

    boolean isUserSubmittedBar(String login);

    List<Entity> getBarList();

    Map<Long, String> getFoodList();

    Map<Long, String> getBeerList();

    boolean submitBeer(String beerName);

    boolean submitFood(String beerName);
}
