package by.siarhei.beerfest.exception;

public class NotProxyConnectionException extends Exception {
    public NotProxyConnectionException() {
    }

    public NotProxyConnectionException(String message) {
        super(message);
    }

    public NotProxyConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotProxyConnectionException(Throwable cause) {
        super(cause);
    }
}
