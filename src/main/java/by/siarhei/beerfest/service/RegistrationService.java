package by.siarhei.beerfest.service;

import by.siarhei.beerfest.exception.ServiceException;

/**
 * Interface represents new user's registration logic.
 */
public interface RegistrationService {

    /**
     * Method presents the defining user's locale based on {@code SessionRequestContent}.
     *
     * @param token represents registration token.
     * @return boolean values that contains registration completing result.
     */
    boolean completeRegistration(String token) throws ServiceException;

    /**
     * Method presents adding registration token logic.
     *
     * @param id represents user's id.
     * @param eMail represents user's eMail.
     */
    void addRegistrationToken(long id, String eMail) throws ServiceException;
}
