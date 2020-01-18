package by.siarhei.beerfest.service;

import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.entity.StatusType;
import by.siarhei.beerfest.entity.User;

public interface AccountService {
    User defineUserById(long id);

    boolean changeUserPassword(String login, String eMail, String newPassword);

    boolean signupUser(String login, String eMail, String password, RoleType role, StatusType active);

    boolean checkUserByLoginPassword(String login, String password);

    User defineUserByLogin(String login);

    boolean chageAvatar(String login, String uploadedFilePath);
}
