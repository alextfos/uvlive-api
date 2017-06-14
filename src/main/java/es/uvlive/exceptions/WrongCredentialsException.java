package es.uvlive.exceptions;

public class WrongCredentialsException extends Exception {
	public static final String MESSAGE = "WrongCredentialsException";
	
	@Override
	public String getMessage() {
		return MESSAGE;
	}
}
