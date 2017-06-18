package es.uvlive.exceptions;

public class ConversationNotCreatedException extends Exception {
	public static final String MESSAGE = "ConversationNotCreatedException";
	
	@Override
	public String getMessage() {
		return MESSAGE;
	}
}
