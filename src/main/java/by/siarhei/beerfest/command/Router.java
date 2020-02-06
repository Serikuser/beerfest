package by.siarhei.beerfest.command;

public class Router {
    public enum TransitionType {
        FORWARD,
        REDIRECT;
    }

    private String uri;
    private TransitionType type;

    public Router(String uri) {
        this.uri = uri;
        this.type = TransitionType.FORWARD;
    }

    public String getUri() {
        return uri;
    }

    public TransitionType getTransitionType() {
        return type;
    }

    public void setRedirect() {
        this.type = TransitionType.REDIRECT;
    }
}
