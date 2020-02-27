package by.siarhei.beerfest.service;

import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.entity.StatusType;
import by.siarhei.beerfest.entity.impl.User;
import by.siarhei.beerfest.exception.ServiceException;

import java.util.List;

/**
 * Interface represents account's processing logic
 */
public interface AccountService {

    /**
     * Method presents logic of defining users by login.
     *
     * @param id represents user's id.
     * @return {@code User} witch contains all user's data.
     */
    User defineUserById(long id) throws ServiceException;

    /**
     * Method presents logic of defining changing password logic.
     *
     * @param login represents user's login.
     * @param eMail represents user's eMail.
     * @param newPassword represents new password.
     */
    void changeUserPassword(String login, String eMail, String newPassword) throws ServiceException;

    /**
     * Method presents new user signup logic.
     *
     * @param login represents user's login.
     * @param eMail represents user's eMail.
     * @param password represents new password.
     * @param role represents value of {@code RoleType}
     * @param status represents values of {@code StatusType}
     */
    void signupUser(String login, String eMail, String password, RoleType role, StatusType status) throws ServiceException;

    /**
     * Method presents validation of input pare <login,eMail>.
     *
     * @param login represents user's login.
     * @param eMail represents user's eMail.
     * @return boolean value whether the entered login and eMail match.
     */
    boolean checkUserByLoginEmail(String login, String eMail) throws ServiceException;

    /**
     * Method presents validation of input pare <login,eMail>.
     *
     * @param login represents user's login.
     * @param password represents user's password.
     * @return boolean value whether the entered login and password match.
     */
    boolean checkUserByLoginPassword(String login, String password) throws ServiceException;

    /**
     * Method presents logic of defining users by login.
     *
     * @param login represents user's login.
     * @return {@code User} witch contains all user's data.
     */
    User defineUserByLogin(String login) throws ServiceException;

    /**
     * Method presents changing avatar logic.
     *
     * @param login represents user's login.
     * @param uploadedFilePath represents new avatar path.
     */
    void changeAvatar(String login, String uploadedFilePath) throws ServiceException;

    /**
     * Method presents logic of defining users by login.
     *
     * @param pageNumber current number page in pagination.
     * @return {@code List<User>} witch contains {@code User} number expected for current page.
     */
    List<User> findUserList(String pageNumber) throws ServiceException;

    /**
     * Method presents count users logic.
     *
     */
    int countUsers() throws ServiceException;

    /**
     * Method presents count pages logic.
     *
     */
    int countPages() throws ServiceException;
}
