package es.uvlive.model.dao;

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

	public Message sendMessage(RolUV user, int idTutorial, String text) throws SQLException, ClassNotFoundException {
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
}
