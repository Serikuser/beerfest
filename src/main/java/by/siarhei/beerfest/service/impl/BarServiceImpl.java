package by.siarhei.beerfest.service.impl;

import by.siarhei.beerfest.dao.BarDao;
import by.siarhei.beerfest.dao.impl.BarDaoImpl;
import by.siarhei.beerfest.entity.impl.Bar;
import by.siarhei.beerfest.exception.DaoException;
import by.siarhei.beerfest.exception.ServiceException;
import by.siarhei.beerfest.service.BarService;
import by.siarhei.beerfest.validator.InputDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class BarServiceImpl implements BarService {
    private static final Logger logger = LogManager.getLogger();

    private BarDao barDao;
    private InputDataValidator validator;

    public BarServiceImpl() {
        validator = new InputDataValidator();
        barDao = new BarDaoImpl();
    }

    @Override
    public boolean submitBar(long accountId, String barName, long beerType, long foodType, String barDescription, String places) throws ServiceException {
        if (isValidInputData(barName, barDescription, places)) {
            Bar bar = new Bar();
            bar.setAccountId(accountId);
            bar.setName(barName);
            bar.setBeerId(beerType);
            bar.setFoodId(foodType);
            int numberOfPlaces = Integer.parseInt(places);
            bar.setPlaces(numberOfPlaces);
            bar.setDescription(barDescription);
            try {
                this.barDao.create(bar);
                return true;
            } catch (DaoException e) {
                logger.error("Cannot submit new bar", e);
                throw new ServiceException(e);
            }
        } else {
            return false;
        }
    }


    @Override
    public boolean checkUserSubmission(String login) throws ServiceException {
        try {
            return !barDao.isUserSubmittedBar(login);
        } catch (DaoException e) {
            logger.error("Cannot check user bar submissions", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void submitBeer(String beerName) throws ServiceException {
        try {
            barDao.submitBeer(beerName);
        } catch (DaoException e) {
            logger.error("Cannot submit new beer type", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void submitFood(String foodName) throws ServiceException {
        try {
            barDao.submitFood(foodName);
        } catch (DaoException e) {
            logger.error("Cannot submit new food type", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Bar> updateParticipants() throws ServiceException {
        try {
            return barDao.findAll();
        } catch (DaoException e) {
            logger.error("Cannot update participants list", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Long, String> updateFoodList() throws ServiceException {
        try {
            return barDao.findAllFoodType();
        } catch (DaoException e) {
            logger.error("Cannot update food list ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Long, String> updateBeerList() throws ServiceException {
        try {
            return barDao.findnAllBeerType();
        } catch (DaoException e) {
            logger.error("Cannot update beer list", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public long findUserByBarId(long userId) {
        long barId = 0;
        try {
            barId = barDao.findBarByUserId(userId);
        } catch (DaoException e) {
            logger.error("Cannot find bar by user id", e);
        }
        return barId;
    }

    private boolean isValidInputData(String barName, String barDescription, String places) {
        return validator.isValidPlaces(places)
                && validator.isBarDescriptionValid(barDescription)
                && validator.isBarNameValid(barName);
    }
}
