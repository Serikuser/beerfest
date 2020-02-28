package by.siarhei.beerfest.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

public class RegistrationTokensJob {
    private static final Logger logger = LogManager.getLogger();

    private static RegistrationTokensJob instance;
    private static final long MINUTES_15 = 900_000L;
    private static final long HOURS_2 = 7_200_000L;
    private TimerTask expiredTokenTask;
    private TimerTask expiredDateTask;

    private RegistrationTokensJob() {
        checkExpiredTokens();
        checkExpiredDate();
    }

    public static RegistrationTokensJob getInstance() {
        if (instance == null) {
            instance = new RegistrationTokensJob();
        }
        return instance;
    }

    private void checkExpiredTokens() {
        if (expiredTokenTask == null) {
            expiredTokenTask = ExpiredTokensTask.getInstance();
            logger.info("Expired tokens job started. Clean up every two hours");
            Timer timer = new Timer("Expired tokens job");
            timer.scheduleAtFixedRate(expiredTokenTask, HOURS_2, HOURS_2);
        }
    }

    private void checkExpiredDate() {
        if (expiredDateTask == null) {
            expiredDateTask = ExpiredDateTask.getInstance();
            logger.info("Expired date job started. Clean up every fifteen minutes");
            Timer timer = new Timer("Expired date job");
            timer.scheduleAtFixedRate(expiredDateTask, MINUTES_15, MINUTES_15);
        }
    }
}
