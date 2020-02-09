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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Router router = (Router) o;

        if (uri != null ? !uri.equals(router.uri) : router.uri != null) {
            return false;
        }
        return type == router.type;
    }

    @Override
    public int hashCode() {
        int result = uri != null ? uri.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
