package es.uvlive.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import es.uvlive.exceptions.ConversationNotCreatedException;
import es.uvlive.model.dao.MessageDAO;
import es.uvlive.model.dao.RolUVDAO;
import es.uvlive.model.dao.UserDAO;

public abstract class RolUV extends User {

	private String pushToken;
	
	private TutorialCatalog tutorialCatalog;
	private Collection<es.uvlive.model.Tutorial> userTutorials;
	private SessionManager sessionManager;
	
	public RolUV() {
		
	}
	
	/**
	 * Initializes User's conversations collection using tutorialCatalog
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void init() throws ClassNotFoundException, SQLException {
		if (tutorialCatalog != null) {
			if (userTutorials == null || userTutorials.isEmpty()) {
				userTutorials = tutorialCatalog.getTutorials(this);
				for (Tutorial tutorial: userTutorials) {
					tutorial.addRolUV(this);
				}
			}
		}
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
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
	public void sendMessage(int idTutorial, String text) throws SQLException, ClassNotFoundException, Exception {
		if (tutorialCatalog == null) {
			throw new Exception();
		}
		
		Tutorial currentTutorial = null;
		
		// Gets the current tutorial from tutorials collection
		for (Tutorial tutorial : userTutorials) {
			if (tutorial.getIdTutorial() == idTutorial) {
				currentTutorial = tutorial;
			}
		}
		
		if (currentTutorial != null) {
			Message message = new MessageDAO().saveMessage(this, idTutorial, text);
			currentTutorial.sendMessage(this,message);
		}
	}

	// TODO @Non-generated
	/**
	 * Sets tutorials catalog
	 * @param tutorialCatalog
	 */
	public void setTutorialsCatalog(TutorialCatalog tutorialCatalog) {
		this.tutorialCatalog = tutorialCatalog;
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
	
	public Collection<Message> getPreviousMessages(int idConversation, int idMessage) throws ClassNotFoundException, SQLException {
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
					messages = new MessageDAO().getPreviousMessages(idConversation, idMessage);
					// TODO update user conversations list with messages
				}
			}
		}
		
		return messages;
	}
	
	public Collection<Message> getFollowingMessages(int idConversation, int idMessage) throws ClassNotFoundException, SQLException {
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
					messages = new MessageDAO().getFollowingMessages(idConversation, idMessage);
					// TODO update user conversations list with messages
				}
			}
		}
		
		return messages;
	}

	public TutorialCatalog getTutorialCatalog() {
		return tutorialCatalog;
	}

	public Collection<Tutorial> getUserTutorials() {
		return userTutorials;
	}

	public SessionManager getSessionManager() {
		return sessionManager;
	}
	
	public void removePushToken() throws SQLException {
		pushToken = null;
		new RolUVDAO().removePushToken(getUserId());
	}

	public abstract Collection<RolUV> getUsers() throws ClassNotFoundException, SQLException;

	public abstract void initConversation(int userId) throws ClassNotFoundException, SQLException, ConversationNotCreatedException;
	
}