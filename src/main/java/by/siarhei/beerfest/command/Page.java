package by.siarhei.beerfest.command;

public class Page {
    public enum Router {
        FORWARD,
        REDIRECT;
    }

    private String uri;
    private Router type;

    public Page(String uri, Router type) {
        this.uri = uri;
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public Router getType() {
        return type;
    }
}
