package by.siarhei.beerfest.dao;

import by.siarhei.beerfest.entity.impl.User;
import by.siarhei.beerfest.exception.DaoException;

import java.util.List;

/**
 * Interface extends generic {@code BaseDao}
 * represents CRUD methods to access table witch contains {@code User} data
 */
public interface UserDao extends BaseDao<Long, User> {

    /**
     * Method reads data from table.
     *
     * @param login represents user's login.
     * @return {@code User} witch contains all user's data.
     */
    User findUserByLogin(String login) throws DaoException;

    /**
     * Method reads data from table.
     *
     * @param login represents user's login.
     * @return boolean values is input password corresponds to login.
     */
    boolean isLoginPasswordMatch(String login, String password) throws DaoException;

    /**
     * Method reads data from table.
     *
     * @param login represents user's login.
     * @return boolean values witch represents is login already exists.
     */
    boolean isExist(String login, String eMail) throws DaoException;

    /**
     * Method updates data in table.
     *
     * @param login represents user's login.
     * @param newPassword represents user's new password.
     */
    void updatePassword(String login, String newPassword) throws DaoException;

    /**
     * Method updates data in table.
     *
     * @param login represents user's login.
     * @param uploadedFilePath represents user's new avatar path.
     */
    void updateAvatar(String login, String uploadedFilePath) throws DaoException;

    /**
     * Method updates data in table.
     *
     * @param login represents user's login.
     * @param status represents user's new {@code StatusType} value.
     */
    void updateStatus(String login, int status) throws DaoException;

    /**
     * Method updates data in table.
     *
     * @param id represents user's id.
     * @param status represents user's new {@code StatusType} value.
     */
    void updateStatusById(long id, int status) throws DaoException;

    /**
     * Method reads data from table.
     *
     * Method counts number of users in table.
     */
    int countUsers() throws DaoException;

    /**
     * Method reads data from table.
     *
     * @param offset represents offset witch need to be done.
     * @return {@code List<User>} witch contains {@code User}
     */
    List<User> findAll(long offset) throws DaoException;
}
