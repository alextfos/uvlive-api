package es.uvlive.exceptions;

public class UserNotDefinedException extends Exception {
	public static final String MESSAGE = "UserNotDefinedException";

	@Override
	public String getMessage() {
		return MESSAGE;
	}
}
