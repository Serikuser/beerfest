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
import java.util.UUID;

public class RegistrationServiceImpl implements RegistrationService {
    private static final Logger logger = LogManager.getLogger();

    private RegistrationDao registrationDao;

    public RegistrationServiceImpl() {
        registrationDao = new RegistrationDaoImpl();
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
            logger.error("Cannot complete registration to id: %s ", e);
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
            logger.error("Cannot create registration to id: %s ", e);
            throw new ServiceException(e);
        }
    }

    private void sendEmail(String token, String eMail) {
        MailThread mailOperator = new MailThread(token, eMail);
        mailOperator.start();
    }
}
