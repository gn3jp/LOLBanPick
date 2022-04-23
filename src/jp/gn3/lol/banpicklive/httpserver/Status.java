package jp.gn3.lol.banpicklive.httpserver;

public enum Status {
    OK("200 OK"),
    NOT_FOUND("404 Not Found");

    private final String text;

    Status(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
