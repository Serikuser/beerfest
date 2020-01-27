package by.siarhei.beerfest.factory;

import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.entity.StatusType;
import by.siarhei.beerfest.entity.impl.User;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserFactory{
    // FIXME: 07.01.2020
    private static UserFactory instance;
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static Lock locker = new ReentrantLock();

    private UserFactory() {
    }

    public static UserFactory getInstance() {
        if (!isCreated.get()) {
            try {
                locker.lock();
                if (instance == null) {
                    instance = new UserFactory();
                    isCreated.set(true);
                }
            } finally {
                locker.unlock();
            }
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
