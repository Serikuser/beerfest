package by.siarhei.beerfest.service;

import by.siarhei.beerfest.dao.BarDAO;
import by.siarhei.beerfest.dao.impl.BarDAOImpl;
import by.siarhei.beerfest.dao.impl.FeedDAOImpl;
import by.siarhei.beerfest.entity.Bar;
import by.siarhei.beerfest.entity.Entity;

import java.util.List;
import java.util.Map;

public class FeedUpdateService {

    public static List<Entity> updateNews() {
        FeedDAOImpl dao = new FeedDAOImpl();
        return dao.findAll();
    }

    public static List<Bar> updateParticipants() {
        BarDAO dao = new BarDAOImpl();
        return  dao.findAll();
    }

    public static Map<Long, String> updateFoodList() {
        BarDAO dao = new BarDAOImpl();
        return  dao.getFoodList();
    }

    public static  Map<Long, String> updateBeerList() {
        BarDAO dao = new BarDAOImpl();
        return  dao.getBeerList();
    }
}
