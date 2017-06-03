package es.uvlive.controllers.exceptions;

public class UserDefinedException extends Exception {
	public static final String MESSAGE = "UserDefinedException";
	
	@Override
	public String getMessage() {
		return MESSAGE;
	}
}
