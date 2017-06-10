package es.uvlive.model.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import es.uvlive.model.Message;
import es.uvlive.model.RolUV;

public class MessageDAO extends BaseDAO {

	private static final String QUERY_SEND_MESSAGE = "INSERT INTO " + MESSAGE_TABLE
			+ " (text, timestamp, ConversationidConversation, RolUVidUser) VALUES (?, ?, ?, ?);";
	
	private static final String QUERY_GET_MESSAGES = "SELECT * FROM " + MESSAGE_TABLE + " WHERE " + MESSAGE_ID_CONVERSATION_FIELD + " = %s";

	public Message saveMessage(RolUV user, int idTutorial, String text) throws SQLException, ClassNotFoundException {
		Message message = new Message();

		String timestamp = String.valueOf(((long) new Date().getTime()) / 1000);

		PreparedStatement preparedStatement = getPreparedStatement(QUERY_SEND_MESSAGE);
		preparedStatement.setString(1, text);
		preparedStatement.setString(2, timestamp);
		preparedStatement.setInt(3, idTutorial);
		preparedStatement.setInt(4, user.getUserId());
		insert(preparedStatement);

		try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				message.setIdMessage(generatedKeys.getInt(1));
				message.setRolUV(user);
				message.setText(text);
				message.setTimestamp(timestamp);
			}
		}

		return message;
	}

	public Collection<Message> getMessages(int idConversation) throws ClassNotFoundException, SQLException {
		Collection<Message> messages = new ArrayList<Message>();
		
        ResultSet result = query(String.format(QUERY_GET_MESSAGES, idConversation));
        if (result != null) {
            while(result.next()) {
                Message message = new Message();
                message.setIdMessage(result.getInt(1));
                // Gets user by id to set RolUV user
                // TODO setRolUV();
                message.setText(result.getString(2));
                message.setTimestamp(result.getString(3));
                
                messages.add(message);
            }
        }
        
		return messages;
	}
}
