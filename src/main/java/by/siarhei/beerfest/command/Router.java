package by.siarhei.beerfest.command;

/**
 * The {@code Router} class represents uri to resource and routing type.
 */
public class Router {
    /**
     * Inner enum of routing types.
     */
    public enum TransitionType {
        FORWARD,
        REDIRECT
    }
    /**
     * The value is used for resource uri storage.
     */
    private String uri;

    /**
     * The value is used for routing type storage.
     */
    private TransitionType type;

    /**
     * Default class constructor; it creates a new Router object with the given url and
     * default routing type - Forward.
     */
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
