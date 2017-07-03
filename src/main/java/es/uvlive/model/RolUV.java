package es.uvlive.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import es.uvlive.exceptions.ConversationNotCreatedException;
import es.uvlive.exceptions.UserBlockedException;
import es.uvlive.model.dao.MessageDAO;
import es.uvlive.model.dao.RolUVDAO;
import es.uvlive.model.dao.UserDAO;
import es.uvlive.utils.DateUtils;

public abstract class RolUV extends User {

	private String pushToken;
	
	private ConversationCatalog conversationCatalog;
	private Collection<Conversation> userConversations;
	private SessionManager sessionManager;
	
	public RolUV() {
		
	}
	
	/**
	 * Initializes User's conversations collection using conversationCatalog
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void init() throws ClassNotFoundException, SQLException {
		if (conversationCatalog != null) {
			if (userConversations == null || userConversations.isEmpty()) {
				userConversations = conversationCatalog.getConversations(this);
				for (Conversation conversation : userConversations) {
					conversation.addRolUV(this);
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
	 * Gets conversations
	 * @return Collection<Conversation> conversation conversations of this user
	 */
	public Collection<Conversation> getConversations() {
		return userConversations;
	}
	/**
	 * 
	 * Sends message
	 * @param idConversation
	 * @param text
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void sendMessage(int idConversation, String text) throws Exception {
		if (conversationCatalog == null) {
			throw new Exception();
		}
		if (this instanceof Student && ((Student)this).isBlocked()) {
			throw new UserBlockedException();
		}
		
		Conversation currentConversation = null;
		
		// Gets the current conversation from conversation collection
		for (Conversation conversation : userConversations) {
			if (conversation.getIdConversation() == idConversation) {
				currentConversation = conversation;
			}
		}
		
		if (currentConversation != null) {
			Message message = new Message();
			message.setTimestamp(DateUtils.getCurrentTimestamp());
			message.setText(text);
			message.setRolUV(this);
			message.setOwner(getFirstname() + " " + getLastname());
			message.setConversation(currentConversation);

			currentConversation.sendMessage(this,message);
		}
	}

	/**
	 * Sets conversation catalog
	 * @param conversationCatalog
	 */
	public void setConversationCatalog(ConversationCatalog conversationCatalog) {
		this.conversationCatalog = conversationCatalog;
	}

	public Collection<Message> getMessages(int idConversation) throws ClassNotFoundException, SQLException {
		Collection<Message> messages = new ArrayList<>();
		
		for (Conversation conversation : userConversations) {
			if (conversation.getIdConversation() == idConversation) {
				messages = new MessageDAO().getMessages(idConversation);
			}
		}
		
		return messages;
	}
	
	public Collection<Message> getPreviousMessages(int idConversation, int idMessage) throws ClassNotFoundException, SQLException {
		Collection<Message> messages = new ArrayList<>();
		
		for (Conversation conversation : userConversations) {
			if (conversation.getIdConversation() == idConversation) {
				messages = new MessageDAO().getPreviousMessages(idConversation, idMessage);
			}
		}
		
		return messages;
	}
	
	public Collection<Message> getFollowingMessages(int idConversation, int idMessage) throws ClassNotFoundException, SQLException {
		Collection<Message> messages = new ArrayList<>();
		Conversation requestedConversation = new Conversation();
		
		for (Conversation conversation : userConversations) {
			if (conversation.getIdConversation() == idConversation) {
				messages = new MessageDAO().getFollowingMessages(idConversation, idMessage);
			}
		}
		
		return messages;
	}

	public ConversationCatalog getConversationCatalog() {
		return conversationCatalog;
	}

	public Collection<Conversation> getUserConversations() {
		return userConversations;
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