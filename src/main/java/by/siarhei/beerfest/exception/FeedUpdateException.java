package by.siarhei.beerfest.exception;

public class FeedUpdateException extends Exception {
    public FeedUpdateException() {
    }

    public FeedUpdateException(String message) {
        super(message);
    }

    public FeedUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public FeedUpdateException(Throwable cause) {
        super(cause);
    }
}
