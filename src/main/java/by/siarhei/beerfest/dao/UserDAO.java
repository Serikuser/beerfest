package by.siarhei.beerfest.dao;

import by.siarhei.beerfest.entity.User;

public interface UserDAO extends BaseDAO<Long, User> {
    User findUserByLogin(String login);

    boolean isExist(String login, String eMail);

    boolean updatePassword(String login, String newPassword);

    boolean updateAvatar(String login, String uploadedFilePath);
}
