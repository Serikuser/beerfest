package by.siarhei.beerfest.service;

import by.siarhei.beerfest.entity.impl.Bar;
import by.siarhei.beerfest.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * Interface represents bar's processing logic
 */
public interface BarService {

    /**
     * Method presents the logic for confirming a new bar.
     *
     * @param accountId represents user's id.
     * @param barName represents bar's name.
     * @param beerType represents beer type.
     * @param foodType represents food type.
     * @param barDescription represents text with bar description.
     * @param places represents number of places in bar.
     * @return boolean value contains add result.
     */
    boolean submitBar(long accountId, String barName, long beerType, long foodType, String barDescription, String places) throws ServiceException;

    /**
     * Method presents the logic for confirming a new bar.
     *
     * @param login represents user's login.
     * @return boolean value contains is user have submitted bar.
     */
    boolean checkUserSubmission(String login) throws ServiceException;

    /**
     * Method presents the logic for adding new beer type.
     *
     * @param beerName represents beer type name.
     */
    void submitBeer(String beerName) throws ServiceException;

    /**
     * Method presents the logic for adding new food type.
     *
     * @param foodName represents food type name.
     */
    void submitFood(String foodName) throws ServiceException;

    /**
     * Method presents the logic viewing bars list.
     *
     * @return {@code List<Bar>} witch contains {@code Bar}.
     */
    List<Bar> updateParticipants() throws ServiceException;

    /**
     * Method presents the logic viewing food type list.
     *
     * @return {@code Map<Long,String>} witch contains pare <id,food type>.
     */
    Map<Long, String> updateFoodList() throws ServiceException;

    /**
     * Method presents the logic viewing beer type list.
     *
     * @return {@code Map<Long,String>} witch contains pare <id,beer type>.
     */
    Map<Long, String> updateBeerList() throws ServiceException;

    /**
     * Method presents the viewing bar owner logic.
     *
     * @param barId represents bar's id.
     * @return long value witch contains owner id.
     */
    long findUserByBarId(long barId);
}
