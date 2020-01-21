package by.siarhei.beerfest.service;

import by.siarhei.beerfest.entity.Bar;
import by.siarhei.beerfest.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface BarService {
    // TODO: 11.01.2020
    void submitBar(long accountId, String barName, long beerType, long foodType, String barDescription, int places) throws ServiceException;

    boolean checkUserSubmission(String login) throws ServiceException;

    void submitBeer(String beerName) throws ServiceException;

    void submitFood(String foodName) throws ServiceException;

    List<Bar> updateParticipants() throws ServiceException;

    Map<Long, String> updateFoodList() throws ServiceException;

    Map<Long, String> updateBeerList() throws ServiceException;
}
