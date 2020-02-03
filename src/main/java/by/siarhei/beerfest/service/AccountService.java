package by.siarhei.beerfest.service;

import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.entity.StatusType;
import by.siarhei.beerfest.entity.impl.User;
import by.siarhei.beerfest.exception.ServiceException;

import java.util.List;

public interface AccountService {
    User defineUserById(long id) throws ServiceException;

    void changeUserPassword(String login, String eMail, String newPassword) throws ServiceException;

    void signupUser(String login, String eMail, String password, RoleType role, StatusType active) throws ServiceException;

    boolean checkUserByLoginEmail(String login, String eMail) throws ServiceException;

    boolean checkUserByLoginPassword(String login, String password) throws ServiceException;

    User defineUserByLogin(String login) throws ServiceException;

    void changeAvatar(String login, String uploadedFilePath) throws ServiceException;

    List<User> findUserList(String pageNumber) throws ServiceException;

    int countUsers() throws ServiceException;

    int countPages() throws ServiceException;
}
