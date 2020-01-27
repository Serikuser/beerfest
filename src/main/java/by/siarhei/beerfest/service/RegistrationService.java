package by.siarhei.beerfest.service;

import by.siarhei.beerfest.exception.ServiceException;

public interface RegistrationService {
    boolean completeRegistration(String token) throws ServiceException;

    void addRegistrationToken(long id, String eMail) throws ServiceException;
}
