package by.siarhei.beerfest.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputDataValidator {
    private static final int MIN_LOGIN_LENGTH = 3;
    private static final int MAX_LOGIN_LENGTH = 20;
    private static final int MIN_EMAIL_LENGTH = 5;
    private static final int MAX_EMAIL_LENGTH = 50;
    private static final int MIN_BAR_NAME_LENGTH = 10;
    private static final int MAX_BAR_NAME_LENGTH = 50;
    private static final int MIN_BAR_DESCRIPTION_LENGTH = 20;
    private static final int MAX_BAR_DESCRIPTION_LENGTH = 100;
    private static final int MIN_BAR_PLACES = 10;
    private static final String REGEX_NON_NUMERIC = "[^\\d. +-]+";
    private static final int MAX_BAR_PLACES = 60;


    public boolean isLoginValid(String login) {
        return !login.isBlank() && login.length() > MIN_LOGIN_LENGTH && login.length() < MAX_LOGIN_LENGTH;
    }

    public boolean isEMailValid(String eMail) {
        return !eMail.isBlank() && eMail.length() > MIN_EMAIL_LENGTH && eMail.length() < MAX_EMAIL_LENGTH;
    }

    public boolean isValidPlaces(String places) {
        if (!places.isBlank() && isNumeric(places)) {
            return Integer.parseInt(places) > MIN_BAR_PLACES
                    && Integer.parseInt(places) < MAX_BAR_PLACES;
        } else {
            return false;
        }
    }

    public boolean isBarDescriptionValid(String barDescription) {
        return !barDescription.isBlank()
                && barDescription.strip().length() > MIN_BAR_DESCRIPTION_LENGTH
                && barDescription.length() < MAX_BAR_DESCRIPTION_LENGTH;
    }

    public boolean isBarNameValid(String barName) {
        return !barName.isBlank()
                && barName.length() > MIN_BAR_NAME_LENGTH
                && barName.length() < MAX_BAR_NAME_LENGTH;
    }

    public boolean isNumeric(String places) {
        Pattern pattern = Pattern.compile(REGEX_NON_NUMERIC);
        Matcher matcher = pattern.matcher(places);
        return !matcher.find();
    }
}
