package by.siarhei.beerfest.service.impl;

import by.siarhei.beerfest.dao.BarDao;
import by.siarhei.beerfest.dao.impl.BarDaoImpl;
import by.siarhei.beerfest.entity.Bar;
import by.siarhei.beerfest.exception.FeedUpdateException;
import by.siarhei.beerfest.service.BarService;

import java.util.List;
import java.util.Map;

public class BarServiceImpl implements BarService {
    private BarDao dao;

    public BarServiceImpl() {
        dao = new BarDaoImpl();
    }

    // TODO: 11.01.2020
    @Override
    public void submitBar(long accountId, String barName, long beerType, long foodType, String barDescription, int places) {
        Bar bar = new Bar();
        bar.setAccountId(accountId);
        bar.setName(barName);
        bar.setBeerId(beerType);
        bar.setFoodId(foodType);
        bar.setPlaces(places);
        bar.setDescription(barDescription);
        dao.create(bar);
    }
    @Override
    public boolean checkUserSubmission(String login) {
        return !dao.isUserSubmittedBar(login);
    }
    @Override
    public boolean submitBeer(String beerName) {
        return dao.submitBeer(beerName);
    }
    @Override
    public boolean submitFood(String foodName) {
        return dao.submitFood(foodName);
    }
    @Override
    public List<Bar> updateParticipants() throws FeedUpdateException {
        return dao.findAll();
    }

    // TODO: 18.01.2020 make entity
    @Override
    public Map<Long, String> updateFoodList() throws FeedUpdateException {
        return dao.findAllFoodType();
    }
    @Override
    public Map<Long, String> updateBeerList() throws FeedUpdateException {
        return dao.findnAllBeerType();
    }
}
