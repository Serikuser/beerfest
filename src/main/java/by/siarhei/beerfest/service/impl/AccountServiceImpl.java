package by.siarhei.beerfest.service.impl;

import by.siarhei.beerfest.dao.UserDao;
import by.siarhei.beerfest.dao.impl.UserDaoImpl;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.entity.StatusType;
import by.siarhei.beerfest.entity.User;
import by.siarhei.beerfest.factory.UserFactory;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.service.AccountService;

public class AccountServiceImpl implements AccountService {
    private static final String PROPERTIES_DEFAULT_AVATAR_URL = "user.data.avatar.default";
    private UserDao dao;

    public AccountServiceImpl() {
        dao = new UserDaoImpl();
    }

    @Override
    public User defineUserById(long id) {
        return dao.findEntity(id);
    }

    @Override
    public boolean changeUserPassword(String login, String eMail, String newPassword) {
        boolean flag = false;
        if (dao.isExist(login, eMail)) {
            return dao.updatePassword(login, newPassword);
        }
        return flag;
    }

    @Override
    public boolean signupUser(String login, String eMail, String password, RoleType role, StatusType active) {
        UserFactory factory = UserFactory.getInstance();
        String avatarUrl = ConfigurationManager.getProperty(PROPERTIES_DEFAULT_AVATAR_URL);
        if (!dao.isExist(login, eMail)) {
            User user = factory.create(login, password, eMail, avatarUrl, role, active);
            return dao.create(user);
        }
        return false;
    }

    @Override
    public boolean checkUserByLoginPassword(String login, String password) {
        return dao.isLoginPasswordMatch(login, password);
    }

    @Override
    public User defineUserByLogin(String login) {
        return dao.findUserByLogin(login);
    }

    @Override
    public boolean chageAvatar(String login, String uploadedFilePath) {
        boolean flag = false;
        if (!uploadedFilePath.isEmpty()) {
            UserDaoImpl dao = new UserDaoImpl();
            flag = dao.updateAvatar(login, uploadedFilePath);
        }
        return flag;
    }
}
