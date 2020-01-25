package by.siarhei.beerfest.dao;

import by.siarhei.beerfest.entity.impl.Registration;
import by.siarhei.beerfest.exception.DaoException;

import java.util.List;

public interface RegistrationDao extends BaseDao<Long, Registration> {
    void updateExpiredByToken(long id, boolean status) throws DaoException;

    Registration findRegistrationByToken(String token) throws DaoException;

    List<Registration> findAllNotExpiredTokens() throws DaoException;

    void deleteExpired() throws DaoException;
}
