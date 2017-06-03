package es.uvlive.controllers.exceptions;

public class UnauthorizedException extends Exception {
	public static final String MESSAGE = "UnauthorizedException";
	
	@Override
	public String getMessage() {
		return MESSAGE;
	}
}
