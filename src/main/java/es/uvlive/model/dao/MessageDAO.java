package es.uvlive.model.dao;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import es.uvlive.model.Message;
import es.uvlive.model.RolUV;

public class MessageDAO extends BaseDAO {

	public Message sendMessage (RolUV user, int idTutorial, String text) throws SQLException {
		Message message = new Message();
		
		String table = "Message";
		String timestamp = String.valueOf(((long)new Date().getTime())/1000);
		
		// TODO do insert in database
		// TODO check in database (Hint: StudentidStudent will be idUser)
		/*String query = "INSERT INTO %s (text, date, StudentidStudent, ConversationidConversation) "+ 
		"VALUES ('%s', '%s', %s, %s);";*/
		/*
		ResultSet result = query(String.format(query, table, text, timestamp, user.getUserId(), idTutorial));
        if(result!=null) {
            while(result.next()) {
            	// TODO evaluate result for getting id
            	// Query for getting Message id
        		int messageId = 1; 
        		message.setIdMessage(messageId);
        		message.setRolUV(user);
        		message.setText(text);
            }
        }
        */
        
		// Stub
		message.setIdMessage(1);
		message.setRolUV(user);
		message.setText(text);
		message.setTimestamp(timestamp);
		
		return message;
	}
}
