package by.siarhei.beerfest.dao;

import by.siarhei.beerfest.entity.impl.User;
import by.siarhei.beerfest.exception.DaoException;

import java.util.List;

public interface UserDao extends BaseDao<Long, User> {
    User findUserByLogin(String login) throws DaoException;

    boolean isLoginPasswordMatch(String login, String password) throws DaoException;

    boolean isExist(String login, String eMail) throws DaoException;

    void updatePassword(String login, String newPassword) throws DaoException;

    void updateAvatar(String login, String uploadedFilePath) throws DaoException;

    void updateStatus(String login, int status) throws DaoException;

    void updateStatusById(long id, int status) throws DaoException;

    int countUsers() throws DaoException;

    List<User> findAll(long offset) throws DaoException;
}
