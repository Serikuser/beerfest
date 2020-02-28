package by.siarhei.beerfest.job;

import by.siarhei.beerfest.dao.RegistrationDao;
import by.siarhei.beerfest.dao.impl.RegistrationDaoImpl;
import by.siarhei.beerfest.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.TimerTask;

public class ExpiredTokensTask extends TimerTask {
    private static final Logger logger = LogManager.getLogger();

    private RegistrationDao registrationDao;
    private static ExpiredTokensTask instance;

    private ExpiredTokensTask() {
        registrationDao = new RegistrationDaoImpl();
    }

    public static ExpiredTokensTask getInstance() {
        if (instance == null) {
            instance = new ExpiredTokensTask();
        }
        return instance;
    }

    @Override
    public void run() {
        try {
            registrationDao.deleteExpired();
        } catch (DaoException e) {
            logger.error("Cannot delete expired tokens ", e);
        }
    }
}
