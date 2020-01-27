package by.siarhei.beerfest.service.impl;

import by.siarhei.beerfest.dao.RegistrationDao;
import by.siarhei.beerfest.dao.TransactionManager;
import by.siarhei.beerfest.dao.impl.RegistrationDaoImpl;
import by.siarhei.beerfest.dao.impl.UserDaoImpl;
import by.siarhei.beerfest.entity.StatusType;
import by.siarhei.beerfest.entity.impl.Registration;
import by.siarhei.beerfest.exception.DaoException;
import by.siarhei.beerfest.exception.ServiceException;
import by.siarhei.beerfest.mail.MailThread;
import by.siarhei.beerfest.service.RegistrationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class RegistrationServiceImpl implements RegistrationService {
    private static final Logger logger = LogManager.getLogger();

    private static RegistrationServiceImpl instance;
    private static final long MINUTES_15 = 900_000L;
    private static final long HOURS_2 = 7_200_000_000_000L;
    private TimerTask expiredTokenObserver;
    private TimerTask expiredDateObserver;
    private RegistrationDao registrationDao;

    private RegistrationServiceImpl() {
        registrationDao = new RegistrationDaoImpl();
        checkExpiredTokens();
        checkExpiredDate();
    }

    public static RegistrationServiceImpl getInstance() {
        if (instance == null) {
            instance = new RegistrationServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean completeRegistration(String token) throws ServiceException {
        boolean isCompleted = false;
        TransactionManager transactionManager = new TransactionManager();
        UserDaoImpl transactionUser = new UserDaoImpl();
        RegistrationDaoImpl transactionRegistration = new RegistrationDaoImpl();
        try {
            Registration registration = registrationDao.findRegistrationByToken(token);
            if (!registration.isExpired() && registration.getToken() != null) {
                int status = StatusType.ACTIVE.getValue();
                long accountId = registration.getAccountId();
                long registrationId = registration.getId();
                transactionManager.openTransaction(transactionUser, transactionRegistration);
                transactionUser.updateStatusById(accountId, status);
                transactionRegistration.updateExpiredByToken(registrationId, true);
                transactionManager.commit();
                isCompleted = true;
            }
        } catch (DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e);
        } finally {
            transactionManager.closeTransaction();
        }
        return isCompleted;
    }

    @Override
    public void addRegistrationToken(long id, String eMail) throws ServiceException {
        String token = UUID.randomUUID().toString();
        Registration registration = new Registration();
        Timestamp date = new Timestamp(System.currentTimeMillis());
        registration.setDate(date);
        registration.setAccountId(id);
        registration.setToken(token);
        registration.setExpired(false);
        try {
            registrationDao.create(registration);
            sendEmail(token, eMail);
        } catch (DaoException e) {
            logger.error(String.format("Cannot create registration to id: %s throws exception: %s", id, e));
            throw new ServiceException(e);
        }
    }

    private void sendEmail(String token, String eMail) {
        MailThread mailOperator = new MailThread(token, eMail);
        mailOperator.start();
    }

    private void checkExpiredTokens() {
        if (expiredTokenObserver == null) {
            expiredTokenObserver = new TimerTask() {
                @Override
                public void run() {
                    try {
                        registrationDao.deleteExpired();
                    } catch (DaoException e) {
                        logger.error(String.format("Cannot delete expired tokens throws exception: %s", e));
                    }
                }
            };
            logger.info("Expired tokens observer started. Clean up every two hours");
            Timer timer = new Timer("Expired tokens observer");
            timer.scheduleAtFixedRate(expiredTokenObserver, HOURS_2, HOURS_2);
        }
    }

    private void checkExpiredDate() {
        if (expiredDateObserver == null) {
            expiredDateObserver = new TimerTask() {
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
                        logger.error(String.format("Cannot update tokens throws exception: %s", e));
                    }
                }
            };
            logger.info("Expired date observer started. Clean up every fifteen minutes");
            Timer timer = new Timer("Expired date observer");
            timer.scheduleAtFixedRate(expiredDateObserver, MINUTES_15, MINUTES_15);
        }
    }
}
