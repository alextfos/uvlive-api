package es.uvlive.model;

import java.sql.SQLException;
import java.util.Collection;

import es.uvlive.model.dao.MessageDAO;

public abstract class RolUV extends User {

	private Collection<Message> messages;
	private String pushToken;
	
	private TutorialCatalog tutorialsCatalog;
	private Collection<es.uvlive.model.Tutorial> userTutorials;
	
	public RolUV() {
		
	}
	
	/**
	 * Initializes User's conversations collection using tutorialCatalog
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void init() throws ClassNotFoundException, SQLException {
		if (tutorialsCatalog != null) {
			if (userTutorials == null || userTutorials.isEmpty()) {
				userTutorials = tutorialsCatalog.getTutorials(this);
			}
		}
		
	}
	
	public String getPushToken() {
		return pushToken;
	}

	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
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
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	// @Non-generated
	public void sendMessage(int idTutorial, String text) throws SQLException, ClassNotFoundException {/*
		if (tutorialsCatalog != null) {
			tutorialsCatalog.sendMessage(this, idTutorial, text);
		}*/
		// Recorrer userTutorials y buscar por ID
		// si no está throw UnauthorizedException
		
		Tutorial currentTutorial = null;
		Message message = new Message();
		
		// Gets the current tutorial from tutorials collection
		for (Tutorial tutorial : userTutorials) {
			if (tutorial.getIdTutorial() == idTutorial) {
				currentTutorial = tutorial;
			}
		}
		
		// Gets messages of current tutorial
//		if (currentTutorial.getMessages() != null && !currentTutorial.getMessages().isEmpty()) {
//			tutorialMessages = currentTutorial.getMessages();
//		}
		
		// Stores message in database and returns a new message model
		message = new MessageDAO().sendMessage(this, idTutorial, text); // TODO change it
		
		currentTutorial.sendMessage(message);
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