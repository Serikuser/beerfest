package by.siarhei.beerfest.observer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

public class RegistrationTokensObserver {
    private static final Logger logger = LogManager.getLogger();

    private static RegistrationTokensObserver instance;
    private static final long MINUTES_15 = 900_000L;
    private static final long HOURS_2 = 7_200_000L;
    private TimerTask expiredTokenObserver;
    private TimerTask expiredDateObserver;

    private RegistrationTokensObserver() {
        checkExpiredTokens();
        checkExpiredDate();
    }

    public static RegistrationTokensObserver getInstance() {
        if (instance == null) {
            instance = new RegistrationTokensObserver();
        }
        return instance;
    }

    private void checkExpiredTokens() {
        if (expiredTokenObserver == null) {
            expiredTokenObserver = ExpiredTokensTask.getInstance();
            logger.info("Expired tokens observer started. Clean up every two hours");
            Timer timer = new Timer("Expired tokens observer");
            timer.scheduleAtFixedRate(expiredTokenObserver, HOURS_2, HOURS_2);
        }
    }

    private void checkExpiredDate() {
        if (expiredDateObserver == null) {
            expiredDateObserver = ExpiredDateTask.getInstance();
            logger.info("Expired date observer started. Clean up every fifteen minutes");
            Timer timer = new Timer("Expired date observer");
            timer.scheduleAtFixedRate(expiredDateObserver, MINUTES_15, MINUTES_15);
        }
    }
}
