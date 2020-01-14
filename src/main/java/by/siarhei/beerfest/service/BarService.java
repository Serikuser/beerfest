package by.siarhei.beerfest.service;

import by.siarhei.beerfest.dao.impl.BarDAOImpl;
import by.siarhei.beerfest.entity.Bar;

public class BarService {

    // TODO: 11.01.2020
    public static void submitBar(long accountId, String barName, long beerType, long foodType, String barDescription, int places) {
        Bar bar = new Bar();
        bar.setAccountId(accountId);
        bar.setName(barName);
        bar.setBeerId(beerType);
        bar.setFoodId(foodType);
        bar.setPlaces(places);
        bar.setDescription(barDescription);
        BarDAOImpl dao = new BarDAOImpl();
        dao.submitBar(bar);
    }

    public static boolean checkUserSubmission(String login) {
        BarDAOImpl dao = new BarDAOImpl();
        return !dao.isUserSubmittedBar(login);
    }

    public static boolean submitBeer(String beerName) {
        BarDAOImpl dao = new BarDAOImpl();
        return dao.submitBeer(beerName);
    }

    public static boolean submitFood(String foodName) {
        BarDAOImpl dao = new BarDAOImpl();
        return dao.submitFood(foodName);
    }
}
