package es.uvlive.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
			message.generateId();
			message.setText(text);
			message.setRolUV(this);
			message.setOwner(getFirstname() + " " + getLastname());
			message.setConversation(currentConversation);

			currentConversation.notifyUsersNewMessage(this,message);
		} else {
			throw new Exception();
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
				messages = conversation.getLastMessages(UVLiveModel.PAGE_SIZE);
				// messages = new MessageDAO().getMessages(idConversation,UVLiveModel.PAGE_SIZE);
			}
		}
		
		return messages;
	}
	
	public Collection<Message> getPreviousMessages(int idConversation, int timestamp) throws ClassNotFoundException, SQLException {
		List<Message> messages = new ArrayList<>();
		
		for (Conversation conversation : userConversations) {
			if (conversation.getIdConversation() == idConversation) {
				if (timestamp > conversation.getFirstMessageTimestamp()) {
					messages = conversation.getLastMessages(UVLiveModel.PAGE_SIZE);
					return messages;
				} else if (conversation.containsTimestamp(timestamp)) {
					messages = conversation.getPreviousMessages(timestamp,UVLiveModel.PAGE_SIZE);
					if (messages.size()<UVLiveModel.PAGE_SIZE && messages.size() > 0) {
						messages.addAll(new MessageDAO().getPreviousMessages(idConversation, messages.get(messages.size()-1).getTimestamp(),UVLiveModel.PAGE_SIZE-messages.size()));
					} else {
						messages.addAll(new MessageDAO().getPreviousMessages(idConversation, timestamp,UVLiveModel.PAGE_SIZE));
					}
					return messages;
				} else {
					messages = new MessageDAO().getPreviousMessages(idConversation, timestamp,UVLiveModel.PAGE_SIZE);
					return messages;
				}
			}
		}
		
		return messages;
	}
	
	public Collection<Message> getFollowingMessages(int idConversation, int timestamp) throws ClassNotFoundException, SQLException {
		List<Message> messages = new ArrayList<>();
		
		for (Conversation conversation : userConversations) {
			if (conversation.getIdConversation() == idConversation) {
				if (timestamp >= conversation.getFirstMessageTimestamp()) {
					return messages;
				} else if (conversation.containsTimestamp(timestamp)) {
					messages = conversation.getFollowingMessages(timestamp,UVLiveModel.PAGE_SIZE);
				} else {
					messages = new MessageDAO().getFollowingMessages(idConversation, timestamp,UVLiveModel.PAGE_SIZE);
				}
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