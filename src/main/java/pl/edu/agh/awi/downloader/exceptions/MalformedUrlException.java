package pl.edu.agh.awi.downloader.exceptions;

public class MalformedUrlException extends RuntimeException {

    private final String parameter;

    public MalformedUrlException(String msg, Throwable throwable) {
        this(msg, throwable, "");
    }

    public MalformedUrlException(String msg, Throwable throwable, String parameter) {
        super(msg, throwable);
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
