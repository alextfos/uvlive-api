package es.uvlive.model.dao;

import java.sql.BatchUpdateException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import es.uvlive.model.Message;

public class MessageDAO extends BaseDAO {

	private static final String QUERY_SAVE_MESSAGE = "INSERT INTO " + MESSAGE_TABLE
			+ " (idMessage,text, timestamp, ConversationidConversation, RolUVidUser) VALUES (?,?, ?, ?, ?);";

	private static final String QUERY_GET_MESSAGES = "SELECT * FROM " + MESSAGE_TABLE + " JOIN " + USER_TABLE + " WHERE " +
			ROL_UV_ID_USER_FIELD  + " = " + USER_ID_FIELD + " AND " + MESSAGE_ID_CONVERSATION_FIELD + " = %d ORDER BY " +
			MESSAGE_ID_FIELD + " DESC LIMIT %d";

	private static final String QUERY_GET_PREVIOUS_MESSAGES = "SELECT * FROM " + MESSAGE_TABLE + " JOIN " + USER_TABLE +
			" WHERE " + ROL_UV_ID_USER_FIELD + " = " + USER_ID_FIELD + " AND " + TIME_STAMP_FIELD + " < %d AND " +
			MESSAGE_ID_CONVERSATION_FIELD + " = %d ORDER BY " + MESSAGE_ID_FIELD + " DESC LIMIT %d";

	private static final String QUERY_GET_FOLLOWING_MESSAGES = "SELECT * FROM " + MESSAGE_TABLE + " JOIN " + USER_TABLE +
			" WHERE " + ROL_UV_ID_USER_FIELD + " = " + USER_ID_FIELD + " AND " + TIME_STAMP_FIELD + " > %d AND " +
			MESSAGE_ID_CONVERSATION_FIELD + " = %d ORDER BY " + MESSAGE_ID_FIELD + " ASC LIMIT %d";

	private static final String QUERY_GET_MAX_MESSAGE_ID = "SELECT MAX(" + MESSAGE_ID_FIELD + ") FROM " + MESSAGE_TABLE;

	public void saveMessages(List<Message> messageList) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = getPreparedStatement(QUERY_SAVE_MESSAGE);

		for (Message message: messageList) {
			preparedStatement.setInt(1,message.getIdMessage());
			preparedStatement.setString(2, message.getText());
			preparedStatement.setLong(3, message.getTimestamp());
			preparedStatement.setInt(4, message.getConversation().getIdConversation());
			preparedStatement.setInt(5, (message.getRolUV() != null)?
                                                        message.getRolUV().getUserId():
                                                        message.getRolUvIdUser());

			preparedStatement.addBatch();
		}
		try {
            insertBatch(preparedStatement);
        } catch (BatchUpdateException e) {
		    // if that exception is thown it's because the element already exists
        }
	}

	public Collection<Message> getMessages(int idConversation, int pageSize) throws ClassNotFoundException, SQLException {
		List<Message> messages = new ArrayList<>();
		
        ResultSet result = query(String.format(QUERY_GET_MESSAGES, idConversation, pageSize));
        if (result != null) {
            while(result.next()) {
                Message message = new Message();

                message.setIdMessage(result.getInt(MESSAGE_ID_FIELD));
                message.setText(result.getString(TEXT_FIELD));
                message.setTimestamp(result.getLong(TIME_STAMP_FIELD));
                message.setOwner(result.getString(FIRST_NAME_FIELD) + " " + result.getString(LAST_NAME_FIELD));
                message.setRolUvIdUser(result.getInt(ROL_UV_ID_USER_FIELD));

                messages.add(message);
            }
        }

		Collections.reverse(messages);

        return messages;
	}
	
	public List<Message> getPreviousMessages(int idConversation, long timestamp, int pageSize) throws ClassNotFoundException, SQLException {
		List<Message> messages = new ArrayList<>();
		
        ResultSet result = query(String.format(QUERY_GET_PREVIOUS_MESSAGES, timestamp, idConversation, pageSize));
        if (result != null) {
            while(result.next()) {
                Message message = new Message();
                message.setIdMessage(result.getInt(MESSAGE_ID_FIELD));
                message.setText(result.getString(TEXT_FIELD));
                message.setTimestamp(result.getLong(TIME_STAMP_FIELD));
                message.setOwner(result.getString(FIRST_NAME_FIELD) + " " + result.getString(LAST_NAME_FIELD));
                message.setRolUvIdUser(result.getInt(ROL_UV_ID_USER_FIELD));

                messages.add(message);
            }
        }
        
		return messages;
	}
	
	public List<Message> getFollowingMessages(int idConversation, long timestamp,int pageSize) throws ClassNotFoundException, SQLException {
		List<Message> messages = new ArrayList<>();
		
        ResultSet result = query(String.format(QUERY_GET_FOLLOWING_MESSAGES, timestamp, idConversation, pageSize));
        if (result != null) {
            while(result.next()) {
                Message message = new Message();

                message.setIdMessage(result.getInt(MESSAGE_ID_FIELD));
                message.setText(result.getString(TEXT_FIELD));
                message.setTimestamp(result.getLong(TIME_STAMP_FIELD));
                message.setOwner(result.getString(FIRST_NAME_FIELD) + " " + result.getString(LAST_NAME_FIELD));
                
                messages.add(message);
            }
        }

        Collections.reverse(messages);
        
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
