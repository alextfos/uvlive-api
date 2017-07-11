package es.uvlive.exceptions;

public class MerchantNotExistsException extends Exception {
	
	public static final String MESSAGE = "MerchantNotExistsException";
	
	@Override
	public String getMessage() {
		return MESSAGE;
	}
}
