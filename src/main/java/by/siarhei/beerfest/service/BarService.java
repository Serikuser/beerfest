package by.siarhei.beerfest.service;

import by.siarhei.beerfest.entity.impl.Bar;
import by.siarhei.beerfest.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface BarService {
    boolean submitBar(long accountId, String barName, long beerType, long foodType, String barDescription, String places) throws ServiceException;

    boolean checkUserSubmission(String login) throws ServiceException;

    void submitBeer(String beerName) throws ServiceException;

    void submitFood(String foodName) throws ServiceException;

    List<Bar> updateParticipants() throws ServiceException;

    Map<Long, String> updateFoodList() throws ServiceException;

    Map<Long, String> updateBeerList() throws ServiceException;

    long findUserByBarId(long userId);
}
