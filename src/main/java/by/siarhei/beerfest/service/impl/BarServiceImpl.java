package by.siarhei.beerfest.service.impl;

import by.siarhei.beerfest.dao.BarDao;
import by.siarhei.beerfest.dao.impl.BarDaoImpl;
import by.siarhei.beerfest.entity.Bar;
import by.siarhei.beerfest.exception.DaoException;
import by.siarhei.beerfest.exception.ServiceException;
import by.siarhei.beerfest.service.BarService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BarServiceImpl implements BarService {
    private static final Logger logger = LogManager.getLogger();

    private static final String REGEX_NON_NUMERIC = "[^\\d. +-]+";
    private BarDao dao;

    public BarServiceImpl() {
        dao = new BarDaoImpl();
    }

    // TODO: 11.01.2020
    @Override
    public boolean submitBar(long accountId, String barName, long beerType, long foodType, String barDescription, int places) throws ServiceException {
        if (isValidData(accountId, barName, beerType, foodType, barDescription, places)) {
            Bar bar = new Bar();
            bar.setAccountId(accountId);
            bar.setName(barName);
            bar.setBeerId(beerType);
            bar.setFoodId(foodType);
            bar.setPlaces(places);
            bar.setDescription(barDescription);
            try {
                dao.create(bar);
                return true;
            } catch (DaoException e) {
                logger.error(String.format("Cannot submit new bar: %s throws exception: %s", barName, e));
                throw new ServiceException(e);
            }
        } else {
            return false;
        }
    }


    @Override
    public boolean checkUserSubmission(String login) throws ServiceException {
        try {
            return !dao.isUserSubmittedBar(login);
        } catch (DaoException e) {
            logger.error(String.format("Cannot check user bar submissions: %s throws exception: %s", login, e));
            throw new ServiceException(e);
        }
    }

    @Override
    public void submitBeer(String beerName) throws ServiceException {
        try {
            dao.submitBeer(beerName);
        } catch (DaoException e) {
            logger.error(String.format("Cannot submit new beer type: %s throws exception: %s", beerName, e));
            throw new ServiceException(e);
        }
    }

    @Override
    public void submitFood(String foodName) throws ServiceException {
        try {
            dao.submitFood(foodName);
        } catch (DaoException e) {
            logger.error(String.format("Cannot submit new food type: %s throws exception: %s", foodName, e));
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Bar> updateParticipants() throws ServiceException {
        try {
            return dao.findAll();
        } catch (DaoException e) {
            logger.error(String.format("Cannot update participants list throws exception: %s", e));
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Long, String> updateFoodList() throws ServiceException {
        try {
            return dao.findAllFoodType();
        } catch (DaoException e) {
            logger.error(String.format("Cannot update food list throws exception: %s", e));
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Long, String> updateBeerList() throws ServiceException {
        try {
            return dao.findnAllBeerType();
        } catch (DaoException e) {
            logger.error(String.format("Cannot update beer list throws exception: %s", e));
            throw new ServiceException(e);
        }
    }

    private boolean isValidData(long accountId, String barName, long beerType, long foodType, String barDescription, int places) {
        return places > 0 && isNumeric(places);
    }

    private boolean isNumeric(Integer places) {
        Pattern pattern = Pattern.compile(REGEX_NON_NUMERIC);
        Matcher matcher = pattern.matcher(places.toString());
        return !matcher.find();
    }

}
