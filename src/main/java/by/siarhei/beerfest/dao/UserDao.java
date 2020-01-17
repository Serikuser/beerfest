package by.siarhei.beerfest.dao;

import by.siarhei.beerfest.entity.User;

public interface UserDao extends BaseDao<Long, User> {
    User findUserByLogin(String login);

    boolean isLoginPasswordMatch(String login, String password);

    boolean isExist(String login, String eMail);

    boolean updatePassword(String login, String newPassword);

    boolean updateAvatar(String login, String uploadedFilePath);
}
