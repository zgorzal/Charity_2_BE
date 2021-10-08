package pl.zgorzal.charity_2_be.exception;

public class AppRequestException extends RuntimeException {

    public AppRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppRequestException(String message) {
        super(message);
    }

}
