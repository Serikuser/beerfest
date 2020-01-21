package by.siarhei.beerfest.manager;

import by.siarhei.beerfest.command.LocaleType;

import java.util.ResourceBundle;

public class MessageManager {

    private MessageManager() {
    }

    public static String getProperty(String key, LocaleType localeType) {
        String property = localeType.getProperty();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(property);
        return resourceBundle.getString(key);
    }
}
