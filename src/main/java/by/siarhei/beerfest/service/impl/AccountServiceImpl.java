package by.siarhei.beerfest.service.impl;

import by.siarhei.beerfest.dao.UserDao;
import by.siarhei.beerfest.dao.impl.UserDaoImpl;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.entity.StatusType;
import by.siarhei.beerfest.entity.impl.User;
import by.siarhei.beerfest.exception.DaoException;
import by.siarhei.beerfest.exception.ServiceException;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.provider.UserProvider;
import by.siarhei.beerfest.service.AccountService;
import by.siarhei.beerfest.validator.InputDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AccountServiceImpl implements AccountService {
    private static final Logger logger = LogManager.getLogger();

    private static final String PROPERTIES_DEFAULT_AVATAR_URL = "user.data.avatar.default";
    private static final int PAGINATION_LIMIT = 5;
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private UserDao userDao;
    private InputDataValidator validator;

    public AccountServiceImpl() {
        validator = new InputDataValidator();
        userDao = new UserDaoImpl();
    }

    @Override
    public User defineUserById(long id) throws ServiceException {
        try {
            return userDao.findEntity(id);
        } catch (DaoException e) {
            logger.error("Cannot find user by id ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeUserPassword(String login, String eMail, String newPassword) throws ServiceException {
        try {
            if (userDao.isExist(login, eMail)) {
                userDao.updatePassword(login, newPassword);
            }
        } catch (DaoException e) {
            logger.error("Cannot change user password", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void signupUser(String login, String eMail, String password, RoleType role, StatusType inactive) throws ServiceException {
        String avatarUrl = ConfigurationManager.getProperty(PROPERTIES_DEFAULT_AVATAR_URL);
        try {
            if (!userDao.isExist(login, eMail)) {
                User user = UserProvider.getInstance().create(login, password, eMail, avatarUrl, role, inactive);
                userDao.create(user);
            }
        } catch (DaoException e) {
            logger.error("Cannot signup new user", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean checkUserByLoginEmail(String login, String eMail) throws ServiceException {
        if (isInputDataValid(login, eMail)) {
            try {
                return !userDao.isExist(login, eMail);
            } catch (DaoException e) {
                logger.error("Cannot check login/email", e);
                throw new ServiceException(e);
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean checkUserByLoginPassword(String login, String password) throws ServiceException {
        try {
            return userDao.isLoginPasswordMatch(login, password);
        } catch (DaoException e) {
            logger.error("Cannot check login/password", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public User defineUserByLogin(String login) throws ServiceException {
        try {
            return userDao.findUserByLogin(login);
        } catch (DaoException e) {
            logger.error("Cannot define user", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeAvatar(String login, String uploadedFilePath) throws ServiceException {
        if (!uploadedFilePath.isEmpty()) {
            try {
                userDao.updateAvatar(login, uploadedFilePath);
            } catch (DaoException e) {
                logger.error("Cannot change avatar to user", e);
                throw new ServiceException(e);
            }
        }
    }

    @Override
    public List<User> findUserList(String pageNumber) throws ServiceException {
        long page;
        if (pageNumber != null && validator.isNumeric(pageNumber)) {
            page = Long.parseLong(pageNumber);
        } else {
            page = DEFAULT_PAGE_NUMBER;
        }
        try {
            long offset = page * PAGINATION_LIMIT;
            return userDao.findAll(offset);
        } catch (DaoException e) {
            logger.error("Cannot find users list", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public int countUsers() throws ServiceException {
        try {
            return userDao.countUsers();
        } catch (DaoException e) {
            logger.error("Cannot count users", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public int countPages() throws ServiceException {
        int count = countUsers();
        int pages = count / PAGINATION_LIMIT;
        if (count % PAGINATION_LIMIT > 0) {
            pages++;
        }
        return pages;
    }

    private boolean isInputDataValid(String login, String eMail) {
        return validator.isLoginValid(login) && validator.isEMailValid(eMail);
    }
}
