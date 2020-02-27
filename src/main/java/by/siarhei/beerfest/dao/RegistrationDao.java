package by.siarhei.beerfest.dao;

import by.siarhei.beerfest.entity.impl.Registration;
import by.siarhei.beerfest.exception.DaoException;

import java.util.List;

/**
 * Interface extends generic {@code BaseDao}
 * represents CRUD methods to access table witch contains {@code Registration} data
 */
public interface RegistrationDao extends BaseDao<Long, Registration> {

    /**
     * Method updates data in table.
     *
     * @param id represents token's id.
     * @param status represents token status (true - expired).
     */
    void updateExpiredByToken(long id, boolean status) throws DaoException;


    /**
     * Method reads data from table.
     *
     * @param token represents token.
     * @return {@code Registration} witch contains all registration token data.
     */
    Registration findRegistrationByToken(String token) throws DaoException;

    /**
     * Method reads data from table.
     *
     * @return {@code List<Registration>} witch contains {@code Registration} with expired status - false.
     */
    List<Registration> findAllNotExpiredTokens() throws DaoException;

    /**
     * Method deletes data from table.
     *
     * Deletes all tokens with expired status - true.
     */
    void deleteExpired() throws DaoException;
}
