package by.siarhei.beerfest.entity;

public enum RoleType {

    UNAUTHORIZED(0, "path.page.main"),

    ADMIN(1, "path.page.admin"),

    GUEST(2, "path.page.guest"),

    PARTICIPANT(3, "path.page.participant");

    private int value;
    private String page;

    RoleType(int value, String page) {
        this.page = page;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getPage() {
        return page;
    }
}
