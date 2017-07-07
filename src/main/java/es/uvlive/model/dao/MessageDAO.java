package es.uvlive.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import es.uvlive.model.Message;
import es.uvlive.model.RolUV;
import es.uvlive.model.UVLiveModel;

public class MessageDAO extends BaseDAO {

	private static final String QUERY_SAVE_MESSAGE = "INSERT INTO " + MESSAGE_TABLE
			+ " (text, timestamp, ConversationidConversation, RolUVidUser) VALUES (?, ?, ?, ?);";

	private static final String QUERY_GET_MESSAGES = "SELECT * FROM " + MESSAGE_TABLE + " JOIN " + USER_TABLE + " WHERE " +
			ROL_UV_ID_USER_FIELD  + " = " + USER_ID_FIELD + " AND " + MESSAGE_ID_CONVERSATION_FIELD + " = %s ORDER BY " +
			TIME_STAMP_FIELD + " DESC LIMIT %d";

	private static final String QUERY_GET_PREVIOUS_MESSAGES = "SELECT * FROM " + MESSAGE_TABLE + " JOIN " + USER_TABLE +
			" WHERE " + ROL_UV_ID_USER_FIELD + " = " + USER_ID_FIELD + " AND " + TIME_STAMP_FIELD + " < %d AND " +
			MESSAGE_ID_CONVERSATION_FIELD + " = %d ORDER BY " + TIME_STAMP_FIELD + " DESC LIMIT %d";

	private static final String QUERY_GET_FOLLOWING_MESSAGES = "SELECT * FROM " + MESSAGE_TABLE + " JOIN " + USER_TABLE +
			" WHERE " + ROL_UV_ID_USER_FIELD + " = " + USER_ID_FIELD + " AND " + TIME_STAMP_FIELD + " > %d AND " +
			MESSAGE_ID_CONVERSATION_FIELD + " = %d ORDER BY " + TIME_STAMP_FIELD + " DESC LIMIT %d";

	private static final String QUERY_GET_MAX_MESSAGE_ID = "SELECT MAX(" + MESSAGE_ID_FIELD + ") FROM " + MESSAGE_TABLE;

	public void saveMessages(List<Message> messageList) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = getPreparedStatement(QUERY_SAVE_MESSAGE);

		for (Message message: messageList) {
			preparedStatement.setString(1, message.getText());
			preparedStatement.setInt(2, message.getTimestamp());
			preparedStatement.setInt(3, message.getConversation().getIdConversation());
			preparedStatement.setInt(4, message.getRolUV().getUserId());

			preparedStatement.addBatch();
		}

		insertBatch(preparedStatement);
	}

	public Collection<Message> getMessages(int idConversation, int pageSize) throws ClassNotFoundException, SQLException {
		Collection<Message> messages = new ArrayList<>();
		
        ResultSet result = query(String.format(QUERY_GET_MESSAGES, idConversation, pageSize));
        if (result != null) {
            while(result.next()) {
                Message message = new Message();
                message.setIdMessage(result.getInt(MESSAGE_ID_FIELD));
                message.setText(result.getString(TEXT_FIELD));
                message.setTimestamp(result.getInt(TIME_STAMP_FIELD));
                message.setOwner(result.getString(FIRST_NAME_FIELD) + " " + result.getString(LAST_NAME_FIELD));
                
                messages.add(message);
            }
        }
        
		return messages;
	}
	
	public List<Message> getPreviousMessages(int idConversation, int timestamp, int pageSize) throws ClassNotFoundException, SQLException {
		List<Message> messages = new ArrayList<Message>();
		
        ResultSet result = query(String.format(QUERY_GET_PREVIOUS_MESSAGES, timestamp, idConversation, pageSize));
        if (result != null) {
            while(result.next()) {
                Message message = new Message();
                message.setIdMessage(result.getInt(MESSAGE_ID_FIELD));
                message.setText(result.getString(TEXT_FIELD));
                message.setTimestamp(result.getInt(TIME_STAMP_FIELD));
                message.setOwner(result.getString(FIRST_NAME_FIELD) + " " + result.getString(LAST_NAME_FIELD));
                
                messages.add(message);
            }
        }
        
		return messages;
	}
	
	public List<Message> getFollowingMessages(int idConversation, int timestamp,int pageSize) throws ClassNotFoundException, SQLException {
		List<Message> messages = new ArrayList<Message>();
		
        ResultSet result = query(String.format(QUERY_GET_FOLLOWING_MESSAGES, timestamp, idConversation, pageSize));
        if (result != null) {
            while(result.next()) {
                Message message = new Message();
                message.setIdMessage(result.getInt(MESSAGE_ID_FIELD));
                message.setText(result.getString(TEXT_FIELD));
                message.setTimestamp(result.getInt(TIME_STAMP_FIELD));
                message.setOwner(result.getString(FIRST_NAME_FIELD) + " " + result.getString(LAST_NAME_FIELD));
                
                messages.add(message);
            }
        }
        
		return messages;
	}

	public int getMaxMessageId() {
		int maxId = 0;
		try {
			ResultSet result = null;
			result = query(QUERY_GET_MAX_MESSAGE_ID);
			if (result != null && result.next()) {
				maxId = result.getInt(1);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return maxId;
	}
}
