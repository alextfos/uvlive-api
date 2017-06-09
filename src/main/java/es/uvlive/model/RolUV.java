package es.uvlive.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import es.uvlive.controllers.messages.MessageListResponse;
import es.uvlive.model.dao.MessageDAO;
import es.uvlive.model.dao.UserDAO;

public abstract class RolUV extends User {

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

	public void setPushToken(String pushToken) throws SQLException {
		this.pushToken = pushToken;
		new UserDAO().savePushToken(getUserId(),pushToken);
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

	public Collection<Message> getMessages(int idConversation) throws ClassNotFoundException, SQLException {
		Collection<Message> messages = new ArrayList<Message>();
		Tutorial requestedTutorial = new Tutorial();
		
		for (Tutorial tutorial : userTutorials) {
			if (tutorial.getIdTutorial() == idConversation) {
				// User can read this conversation
				requestedTutorial = tutorial;
				if (requestedTutorial.getMessages() != null && !requestedTutorial.getMessages().isEmpty()){
					messages = requestedTutorial.getMessages();
				} else {
					// Should get conversations messages
					messages = new MessageDAO().getMessages(idConversation);
					// TODO update user conversations list with messages
				}
			}
		}
		
		return messages;
	}

	public abstract Collection<RolUV> getUsers() throws ClassNotFoundException, SQLException;
	
}