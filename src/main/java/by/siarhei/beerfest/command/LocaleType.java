package by.siarhei.beerfest.command;

public enum LocaleType {
    RU("message_ru"),
    EN("message_en"),
    BY("message_by");
    private String property;

    LocaleType(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }
}
