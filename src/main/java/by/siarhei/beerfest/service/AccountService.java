package by.siarhei.beerfest.service;

import by.siarhei.beerfest.dao.UserDAO;
import by.siarhei.beerfest.dao.impl.UserDAOImpl;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.entity.StatusType;
import by.siarhei.beerfest.entity.User;
import by.siarhei.beerfest.factory.UserFactory;
import by.siarhei.beerfest.manager.ConfigurationManager;

// FIXME: 13.01.2020
public class AccountService {
    private static final String PROPERTIES_DEFAULT_AVATAR_URL = "user.data.avatar.default";

    public static User defineUserById(long id) {
        UserDAOImpl dao = new UserDAOImpl();
        return dao.findUserById(id);
    }

    public static boolean changeUserPassword(String login, String eMail, String newPassword) {
        boolean flag = false;
        UserDAOImpl dao = new UserDAOImpl();
        if (dao.isExist(login, eMail)) {
            return dao.updatePassword(login, newPassword);
        }
        return flag;
    }

    public static boolean signupUser(String login, String eMail, String password, RoleType role, StatusType active) {
        UserDAO dao = new UserDAOImpl();
        UserFactory factory = UserFactory.getInstance();
        String avatarUrl = ConfigurationManager.getProperty(PROPERTIES_DEFAULT_AVATAR_URL);
        if (!dao.isExist(login, eMail)) {
            User user = factory.create(login, password, eMail, avatarUrl, role, active);
            return dao.create(user);
        }
        return false;
    }

    public static boolean checkUserByLoginPassword(String login, String password) {
        UserDAOImpl dao = new UserDAOImpl();
        return dao.isLoginPasswordMatch(login, password);
    }

    public static User defineUserByLogin(String login) {
        UserDAOImpl dao = new UserDAOImpl();
        return dao.findUserByLogin(login);
    }
}
