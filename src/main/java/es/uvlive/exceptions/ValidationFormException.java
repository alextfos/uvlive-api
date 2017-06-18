package es.uvlive.exceptions;

public class ValidationFormException extends Exception {
	public static final String MESSAGE = "ValidationFormException";

	@Override
	public String getMessage() {
		return MESSAGE;
	}
}
