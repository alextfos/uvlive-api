package es.uvlive.controllers.exceptions;

public class TokenExpiredException extends Exception {
	public static final String MESSAGE = "TokenExpiredException";
	
	@Override
	public String getMessage() {
		return MESSAGE;
	}
}
