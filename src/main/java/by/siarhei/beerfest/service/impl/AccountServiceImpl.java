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

public class AccountServiceImpl implements AccountService {
    private static final Logger logger = LogManager.getLogger();

    private static final String PROPERTIES_DEFAULT_AVATAR_URL = "user.data.avatar.default";
    private UserDao dao;
    private InputDataValidator validator;

    public AccountServiceImpl() {
        validator = new InputDataValidator();
        dao = new UserDaoImpl();
    }

    @Override
    public User defineUserById(long id) throws ServiceException {
        try {
            return dao.findEntity(id);
        } catch (DaoException e) {
            logger.error("Cannot find user by id ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeUserPassword(String login, String eMail, String newPassword) throws ServiceException {
        try {
            if (dao.isExist(login, eMail)) {
                dao.updatePassword(login, newPassword);
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
            if (!dao.isExist(login, eMail)) {
                User user = UserProvider.getInstance().create(login, password, eMail, avatarUrl, role, inactive);
                dao.create(user);
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
                return !dao.isExist(login, eMail);
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
            return dao.isLoginPasswordMatch(login, password);
        } catch (DaoException e) {
            logger.error("Cannot check login/password", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public User defineUserByLogin(String login) throws ServiceException {
        try {
            return dao.findUserByLogin(login);
        } catch (DaoException e) {
            logger.error("Cannot define user", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeAvatar(String login, String uploadedFilePath) throws ServiceException {
        if (!uploadedFilePath.isEmpty()) {
            try {
                dao.updateAvatar(login, uploadedFilePath);
            } catch (DaoException e) {
                logger.error("Cannot change avatar to user", e);
                throw new ServiceException(e);
            }
        }
    }

    private boolean isInputDataValid(String login, String eMail) {
        return validator.isLoginValid(login) && validator.isEMailValid(eMail);
    }
}
