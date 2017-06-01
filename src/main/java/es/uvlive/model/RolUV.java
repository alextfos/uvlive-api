package es.uvlive.model;

import java.util.*;

public abstract class RolUV extends User {

	private Collection<Message> messages;
	private String pushToken;

	/**
	 * 
	 * @param tutorial
	 * @param text
	 */
	public void sendMessage(Tutorial tutorial, String text) {
		// TODO - implement RolUV.sendMessage
		throw new UnsupportedOperationException();
	}

}