package by.siarhei.beerfest.factory;

import by.siarhei.beerfest.entity.Bar;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BarFactory {
    // FIXME: 07.01.2020
    private static BarFactory instance;
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static Lock locker = new ReentrantLock();

    private BarFactory() {
    }

    public static BarFactory getInstance() {
        if (!isCreated.get()) {
            try {
                locker.lock();
                if (instance == null) {
                    instance = new BarFactory();
                    isCreated.set(true);
                }
            } finally {
                locker.unlock();
            }
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
