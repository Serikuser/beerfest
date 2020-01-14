package by.siarhei.beerfest.entity;

public enum StatusType {
    ACTIVE(1),

    BANNED(2),

    INACTIVE(3);

    private int value;

    StatusType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
