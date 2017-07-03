package es.uvlive.model;

import java.sql.SQLException;
import java.util.Collection;

import es.uvlive.exceptions.ConversationNotCreatedException;
import es.uvlive.model.dao.RolUVDAO;
import es.uvlive.model.dao.TeacherDAO;
import es.uvlive.model.dao.UserDAO;

public class Teacher extends RolUV {
	
	public static final String LOGIN_TYPE = "Teacher";
	
	/**
	 * Calls PermissionsDAO method and changes student status to blocked
	 * @param idStudent
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void blockStudent(int idStudent) throws ClassNotFoundException, SQLException {
		new TeacherDAO().bockStudent(idStudent);
		getSessionManager().blockUser(idStudent);
	}
	
	/**
	 * Calls PermissionsDAO method and changes student status to unblocked
	 * @param idStudent
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void unblockStudent(int idStudent) throws ClassNotFoundException, SQLException {
		new TeacherDAO().unblockStudent(idStudent);
		getSessionManager().unblockUser(idStudent);
	}

	@Override
	public Collection<RolUV> getUsers() throws ClassNotFoundException, SQLException {
		return new UserDAO().getStudents();
	}

	@Override
	public void initConversation(int userId) throws ClassNotFoundException, SQLException, ConversationNotCreatedException {
		int conversationId = new RolUVDAO().saveConversation(getUserId(),userId);
		Conversation conversation = getConversationCatalog().addAndGetConversation(conversationId);
		conversation.addRolUV(this);
		getUserConversations().add(conversation);
		getSessionManager().addConversationToUser(userId, conversation);
	}

}