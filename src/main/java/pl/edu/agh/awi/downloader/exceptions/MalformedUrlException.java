package pl.edu.agh.awi.downloader.exceptions;

public class MalformedUrlException extends RuntimeException {

    public MalformedUrlException(String s, Throwable throwable) {
        super(s, throwable);
    }

}
