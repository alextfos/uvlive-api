package es.uvlive.exceptions;

public class UserBlockedException extends Exception {
    public static final String MESSAGE = "UserBlockedException";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
