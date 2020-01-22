package by.siarhei.beerfest.service;

import by.siarhei.beerfest.exception.ServiceException;

public interface RegistrationService {
    void completeRegistration(String token) throws ServiceException;

    void addRegistrationToken(String login, String eMail) throws ServiceException;
}
