package by.siarhei.beerfest.service.impl;

import by.siarhei.beerfest.dao.UserDao;
import by.siarhei.beerfest.dao.impl.UserDaoImpl;
import by.siarhei.beerfest.entity.StatusType;
import by.siarhei.beerfest.exception.DaoException;
import by.siarhei.beerfest.exception.ServiceException;
import by.siarhei.beerfest.mail.MailThread;
import by.siarhei.beerfest.service.RegistrationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RegistrationServiceImpl implements RegistrationService {
    private static final Logger logger = LogManager.getLogger();

    private static RegistrationServiceImpl instance;
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static Lock locker = new ReentrantLock();
    private Map<String, String> registrationQueue;
    private UserDao dao;

    private RegistrationServiceImpl() {
        registrationQueue = new HashMap<>();
        dao = new UserDaoImpl();
    }

    public static RegistrationServiceImpl getInstance() {
        if (!isCreated.get()) {
            try {
                locker.lock();
                if (instance == null) {
                    instance = new RegistrationServiceImpl();
                    isCreated.set(true);
                }
            } finally {
                locker.unlock();
            }
        }
        return instance;
    }

    @Override
    public void completeRegistration(String token) throws ServiceException {
        String login = registrationQueue.get(token);
        if (login != null) {
            try {
                int status = StatusType.ACTIVE.getValue();
                dao.updateStatus(login, status);
                registrationQueue.remove(token);
            } catch (DaoException e) {
                logger.error(String.format("Cannot update status throws exception %s", e));
                throw new ServiceException(e);
            }
        } else {
            throw new ServiceException("No token value found");
        }
    }

    @Override
    public void addRegistrationToken(String login, String eMail) throws ServiceException {
        String token = UUID.randomUUID().toString();
        while (registrationQueue.get(token) != null) {
            token = UUID.randomUUID().toString();
        }
        registrationQueue.put(token, login);
        sendEmail(token, eMail);
    }

    private void sendEmail(String token, String eMail) {
        MailThread mailOperator = new MailThread(token, eMail);
        mailOperator.start();
    }
}
