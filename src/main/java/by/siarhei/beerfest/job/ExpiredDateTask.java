package by.siarhei.beerfest.job;

import by.siarhei.beerfest.dao.RegistrationDao;
import by.siarhei.beerfest.dao.impl.RegistrationDaoImpl;
import by.siarhei.beerfest.entity.impl.Registration;
import by.siarhei.beerfest.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.TimerTask;

public class ExpiredDateTask extends TimerTask {
    private static final Logger logger = LogManager.getLogger();

    private static ExpiredDateTask instance;
    private static final long MINUTES_15 = 900_000L;
    private RegistrationDao registrationDao;

    private ExpiredDateTask() {
        registrationDao = new RegistrationDaoImpl();
    }

    public static ExpiredDateTask getInstance() {
        if (instance == null) {
            instance = new ExpiredDateTask();
        }
        return instance;
    }

    @Override
    public void run() {
        try {
            List<Registration> list = registrationDao.findAllNotExpiredTokens();
            if (!list.isEmpty()) {
                for (Registration registration : list) {
                    if (registration.getDate().getTime() + MINUTES_15 < System.currentTimeMillis()) {
                        long id = registration.getId();
                        registrationDao.updateExpiredByToken(id, true);
                    }
                }
            }
        } catch (DaoException e) {
            logger.error("Cannot update tokens ", e);
        }
    }
}
