package es.uvlive.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.uvlive.exceptions.ConversationNotCreatedException;

public class RolUVDAO extends BaseDAO {
	
	private static final String QUERY_GET_STUDENT = "SELECT " + USER_ID_FIELD + " FROM " + USER_TABLE + " NATURAL JOIN " + STUDENT_TABLE + " WHERE " + USER_ID_FIELD + "=%d";
	private static final String QUERY_GET_TEACHER = "SELECT " + USER_ID_FIELD + " FROM " + USER_TABLE + " NATURAL JOIN " + TEACHER_TABLE + " WHERE " + USER_ID_FIELD + "=%d";
	private static final String QUERY_GET_COMMON_CONVERSATIONS = "SELECT COUNT(DISTINCT " + CONVERSATION_ID_CONVERSATION_FIELD + ") FROM " + CONVERSATION_ROL_UV_TABLE + " WHERE "
	+ ROL_UV_ID_USER_FIELD + " = %d OR " + ROL_UV_ID_USER_FIELD + " = %d GROUP BY " + CONVERSATION_ID_CONVERSATION_FIELD;
	// And add a parameter to dinstinct personal conversation to group conversation
	private static final String QUERY_GET_MAX_ID = "SELECT MAX(" + CONVERSATION_ID_FIELD + ") FROM " + CONVERSATION_TABLE;
	private static final String QUERY_INSERT_CONVERSATION = "INSERT INTO " + CONVERSATION_TABLE + " (" + CONVERSATION_ID_FIELD + ", " + NAME_FIELD + ") VALUES (?,?);";
	private static final String QUERY_INSERT_CONVERSATION_ROL_UV = "INSERT INTO " + CONVERSATION_ROL_UV_TABLE + " (" + CONVERSATION_ID_CONVERSATION_FIELD + "," + ROL_UV_ID_USER_FIELD +  ") VALUES (?,?)";
	
	private static final String QUERY_REMOVE_PUSH_TOKEN = "UPDATE " + ROL_UV_TABLE + " SET " + PUSH_TOKEN_FIELD + " = '' WHERE " + USER_ID_FIELD + "= %d";
	
	public int saveConversation(int teacherId, int studentId) throws ClassNotFoundException, SQLException, ConversationNotCreatedException {
		ResultSet studentResult = query(String.format(QUERY_GET_STUDENT,studentId));
		ResultSet teacherResult =  query(String.format(QUERY_GET_TEACHER, teacherId));
		if (studentResult != null && studentResult.next() && teacherResult != null && teacherResult.next()) {
			int s = studentResult.getInt(USER_ID_FIELD);
			int t = teacherResult.getInt(USER_ID_FIELD);
			ResultSet conversationCountQuery = query(String.format(QUERY_GET_COMMON_CONVERSATIONS, teacherId,studentId));
			int conversationCount = Integer.MAX_VALUE;
			if (conversationCountQuery != null) {
				conversationCount = 0;
				while (conversationCountQuery.next()) {
					conversationCount++;
				}
			}
			if (s == studentId && t == teacherId && conversationCount < 1) {
				int maxConversationId = getMaxConversationId();
				if (maxConversationId != 0) {
					maxConversationId++;
					PreparedStatement insertQuery = getPreparedStatement(QUERY_INSERT_CONVERSATION);
					insertQuery.setInt(1,maxConversationId);
					insertQuery.setString(2, "");
					
					insert(insertQuery);
					
					PreparedStatement conversationStudent = getPreparedStatement(QUERY_INSERT_CONVERSATION_ROL_UV);
					PreparedStatement conversationTeacher = getPreparedStatement(QUERY_INSERT_CONVERSATION_ROL_UV);
					
					conversationStudent.setInt(1,maxConversationId);
					conversationTeacher.setInt(1,maxConversationId);
					conversationTeacher.setInt(2, teacherId);
					conversationStudent.setInt(2, studentId);
					
					insert(conversationStudent);
					insert(conversationTeacher);
					
					return maxConversationId;
				}
			} else {
				throw new ConversationNotCreatedException();
			}
			
		} else {
			throw new ConversationNotCreatedException();
		}
		return 0;
	}
	
	private int getMaxConversationId() throws ClassNotFoundException, SQLException {
		ResultSet resultSet = query(QUERY_GET_MAX_ID);
		
		if (resultSet != null && resultSet.next()) {
			return resultSet.getInt(1);
		} else {
			return 0;
		}
		
	}
	
	public void removePushToken(int rolUVId) throws SQLException {
		update(String.format(QUERY_REMOVE_PUSH_TOKEN, rolUVId));
	}
}
