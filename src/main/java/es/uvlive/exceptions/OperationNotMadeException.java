package es.uvlive.exceptions;


public class OperationNotMadeException extends Exception{
    public static final String MESSAGE = "OperationNotMadeException";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
