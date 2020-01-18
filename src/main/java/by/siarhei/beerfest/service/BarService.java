package by.siarhei.beerfest.service;

import by.siarhei.beerfest.entity.Bar;
import by.siarhei.beerfest.exception.FeedUpdateException;

import java.util.List;
import java.util.Map;

public interface BarService {
    // TODO: 11.01.2020
    void submitBar(long accountId, String barName, long beerType, long foodType, String barDescription, int places);

    boolean checkUserSubmission(String login);

    boolean submitBeer(String beerName);

    boolean submitFood(String foodName);

    List<Bar> updateParticipants() throws FeedUpdateException;

    // TODO: 18.01.2020 make entity
    Map<Long, String> updateFoodList() throws FeedUpdateException;

    Map<Long, String> updateBeerList() throws FeedUpdateException;
}
