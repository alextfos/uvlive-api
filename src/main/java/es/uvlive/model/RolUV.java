package es.uvlive.model;

import java.util.Collection;

public abstract class RolUV extends User {

	private Collection<Message> messages;
	private String pushToken;

	private TutorialCatalog tutorialsCatalog;
	private Collection<es.uvlive.model.Tutorial> userTutorials;
	
	public RolUV() {
		
	}
	
	/**
	 * Initializes User's conversations collection using tutorialCatalog
	 */
	public void init() {
		if (tutorialsCatalog != null) {
			if (userTutorials == null || userTutorials.isEmpty()) {
				userTutorials = tutorialsCatalog.getTutorials(this);
			}
		}
		
	}
	
	/**
	 * Gets tutorials
	 * @return Collection<Tutorial> tutorial conversations of this user
	 */
	public Collection<es.uvlive.model.Tutorial> getTutorials() {
		return userTutorials;
	}
	/**
	 * 
	 * Sends message
	 * @param tutorial
	 * @param text
	 */
	public void sendMessage(Tutorial tutorial, String text) {
		// TODO - implement RolUV.sendMessage
		throw new UnsupportedOperationException();
	}

	// TODO @Non-generated
	/**
	 * Gets tutorial catalog
	 * @return tutorialsCatalog
	 */
	public TutorialCatalog getTutorialsCatalog() {
		return tutorialsCatalog;
	}

	// TODO @Non-generated
	/**
	 * Sets tutorials catalog
	 * @param tutorialCatalog
	 */
	public void setTutorialsCatalog(TutorialCatalog tutorialCatalog) {
		this.tutorialsCatalog = tutorialCatalog;
	}
}