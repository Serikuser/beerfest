package by.siarhei.beerfest.entity.impl.provider;

import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.entity.StatusType;
import by.siarhei.beerfest.entity.impl.User;

public class UserProvider {
    private static UserProvider instance;

    private UserProvider() {
    }

    public static UserProvider getInstance() {
        if (instance == null) {
            instance = new UserProvider();
        }
        return instance;
    }

    public User create(String login, String password, String email, String avatarUrl, RoleType role, StatusType status) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setAvatarUrl(avatarUrl);
        user.setRole(role);
        user.setStatus(status);
        return user;
    }
}
