package es.uvlive.model;

import java.sql.SQLException;
import java.util.Collection;

import es.uvlive.exceptions.ConversationNotCreatedException;
import es.uvlive.model.dao.RolUVDAO;
import es.uvlive.model.dao.UserDAO;

public class Student extends RolUV {
	
	public static final String LOGIN_TYPE = "Student";

	private boolean blocked;

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	@Override
	public Collection<RolUV> getUsers() throws ClassNotFoundException, SQLException {
		return new UserDAO().getTeachers();
	}

	@Override
	public void initConversation(int userId) throws ClassNotFoundException, SQLException, ConversationNotCreatedException {
		int conversationId = new RolUVDAO().saveConversation(userId,getUserId());
		Conversation conversation = getConversationCatalog().addAndGetConversation(conversationId);
		conversation.addRolUV(this);
		userConversations.put(conversationId, conversation);
		getSessionManager().addConversationToUser(userId, conversation);
	}
	
}