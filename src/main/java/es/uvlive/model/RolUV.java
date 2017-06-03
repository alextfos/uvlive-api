package es.uvlive.model;

import java.util.Collection;

public abstract class RolUV extends User {

	private Collection<Message> messages;
	private String pushToken;

	private TutorialCatalog tutorialsCatalog;
	private Collection<es.uvlive.model.Tutorial> userTutorials;
	
	public RolUV() {
		
	}
	
	public Collection<es.uvlive.model.Tutorial> getTutorials() {
		
		if (userTutorials == null || userTutorials.isEmpty()) {
			userTutorials = tutorialsCatalog.getTutorials(this);
		}
		
		return userTutorials;
	}
	/**
	 * 
	 * @param tutorial
	 * @param text
	 */
	public void sendMessage(Tutorial tutorial, String text) {
		// TODO - implement RolUV.sendMessage
		throw new UnsupportedOperationException();
	}

	// TODO @Non-generated
	public TutorialCatalog getTutorialsCatalog() {
		return tutorialsCatalog;
	}

	// TODO @Non-generated
	public void setTutorialsCatalog(TutorialCatalog tutorialCatalog) {
		this.tutorialsCatalog = tutorialCatalog;
	}
}