package by.siarhei.beerfest.dao;

import by.siarhei.beerfest.entity.User;
import by.siarhei.beerfest.exception.DaoException;

public interface UserDao extends BaseDao<Long, User> {
    User findUserByLogin(String login) throws DaoException;

    boolean isLoginPasswordMatch(String login, String password) throws DaoException;

    boolean isExist(String login, String eMail) throws DaoException;

    void updatePassword(String login, String newPassword) throws DaoException;

    void updateAvatar(String login, String uploadedFilePath) throws DaoException;
}
