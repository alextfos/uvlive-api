package es.uvlive.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import es.uvlive.model.Conversation;
import es.uvlive.model.User;

public class ConversationDAO extends BaseDAO {
	
	private static final String QUERY_GET_USER_IDS = "SELECT * FROM " + CONVERSATION_ROL_UV_TABLE + " WHERE " + ROL_UV_ID_USER_FIELD + " = '%s'";
	private static final String QUERY_GET_CONVERSATIONS = "SELECT * FROM " + CONVERSATION_TABLE;
	private static final String QUERY_GET_CONVERSATIONS_FROM_ID = "SELECT * FROM " + CONVERSATION_TABLE + " WHERE " + CONVERSATION_ID_FIELD + "=%d";

	public Collection<Conversation> getConversations() throws ClassNotFoundException, SQLException {
		Collection<Conversation> conversationCollection = new ArrayList<>();
		
        ResultSet result = query(QUERY_GET_CONVERSATIONS);
        if (result!=null) {
            while(result.next()) {
            	Conversation conversation = new Conversation(result.getInt(CONVERSATION_ID_FIELD));
                String  name = result.getString(NAME_FIELD);
                conversation.setName(name);
                conversationCollection.add(conversation);
            }
        }
        return conversationCollection;
	}

	public ArrayList<Integer> getIdsOfUserConversations(User user) throws SQLException, ClassNotFoundException {
		
		ArrayList<Integer> idsOfConversationCollection = new ArrayList<>();
			
        ResultSet result = query(String.format(QUERY_GET_USER_IDS, String.valueOf(user.getUserId())));
        if (result != null) {
            while(result.next()) {
                int idConversation = result.getInt(CONVERSATION_ID_CONVERSATION_FIELD);
                idsOfConversationCollection.add(idConversation);
            }
        }
        return idsOfConversationCollection;
	}

    public Conversation getConversation(int idConversation) throws ClassNotFoundException, SQLException {
		ResultSet resultSet = query(String.format(QUERY_GET_CONVERSATIONS_FROM_ID,idConversation));
		Conversation conversation = null;
		
		if (resultSet != null && resultSet.next()) {
			conversation = new Conversation(resultSet.getInt(CONVERSATION_ID_FIELD));
            String  name = resultSet.getString(NAME_FIELD);
            conversation.setName(name);
		}
		return conversation;

    }
}

