package by.siarhei.beerfest.entity.impl.provider;

import by.siarhei.beerfest.entity.impl.Bar;

public class BarProvider {
    private static BarProvider instance;

    private BarProvider() {
    }

    public static BarProvider getInstance() {
        if (instance == null) {
            instance = new BarProvider();
        }
        return instance;
    }

    public Bar create(long barId, long accountId, String name, String description, long foodId, String foodName, long beerId, String beerName, int places) {
        Bar bar = new Bar();
        bar.setId(barId);
        bar.setAccountId(accountId);
        bar.setName(name);
        bar.setDescription(description);
        bar.setFoodId(foodId);
        bar.setFoodName(foodName);
        bar.setBeerId(beerId);
        bar.setBeerName(beerName);
        bar.setPlaces(places);
        return bar;
    }
}
