package by.siarhei.beerfest.provider;

import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.entity.StatusType;
import by.siarhei.beerfest.entity.impl.User;

public class UserProviderImpl {
    private static UserProviderImpl instance;

    private UserProviderImpl() {
    }

    public static UserProviderImpl getInstance() {
        if (instance == null) {
            instance = new UserProviderImpl();
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
