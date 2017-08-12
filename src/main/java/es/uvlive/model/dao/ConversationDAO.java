package es.uvlive.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import es.uvlive.model.Conversation;
import es.uvlive.model.PersonalConversation;
import es.uvlive.model.User;
import es.uvlive.utils.StringUtils;

public class ConversationDAO extends BaseDAO {
	
	private static final String QUERY_GET_USER_IDS = "SELECT * FROM " + CONVERSATION_ROL_UV_TABLE + " WHERE " + ROL_UV_ID_USER_FIELD + " = '%s'";
	private static final String QUERY_GET_CONVERSATIONS = "SELECT * FROM " + CONVERSATION_TABLE;
	private static final String QUERY_GET_CONVERSATIONS_FROM_ID = "SELECT * FROM " + CONVERSATION_TABLE + " WHERE " + CONVERSATION_ID_FIELD + "=%d";
	private static final String QUERY_GET_PERSONAL_CONVERSATION_NAMES = "SELECT " + FIRST_NAME_FIELD + ", " + LAST_NAME_FIELD +
            " FROM " + CONVERSATION_TABLE + " JOIN " + CONVERSATION_ROL_UV_TABLE + " JOIN " + USER_TABLE +
            " WHERE " + CONVERSATION_ID_FIELD + "=" + CONVERSATION_ID_CONVERSATION_FIELD + " AND " + ROL_UV_ID_USER_FIELD +
            "=" + USER_ID_FIELD + " AND " + CONVERSATION_ID_FIELD + "=%d";

	public Collection<Conversation> getConversations() throws ClassNotFoundException, SQLException {
		Collection<Conversation> conversationCollection = new ArrayList<>();
		
        ResultSet result = query(QUERY_GET_CONVERSATIONS);
        if (result!=null) {
            while(result.next()) {
                conversationCollection.add(getConversationByResultSet(result));
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

	private Conversation getConversationByResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        Conversation conversation;
        String  name = resultSet.getString(NAME_FIELD);
        int idConversation = resultSet.getInt(CONVERSATION_ID_FIELD);

        if (!StringUtils.isEmpty(name)) {
            conversation = new Conversation(idConversation);
            conversation.setName(name);
        } else {
            ResultSet subResult = query(String.format(QUERY_GET_PERSONAL_CONVERSATION_NAMES,idConversation));
            conversation = new PersonalConversation(idConversation);
            if (subResult!= null && subResult.next()) {
                ((PersonalConversation)conversation).setParticipant1(subResult.getString(FIRST_NAME_FIELD) + " " + subResult.getString(LAST_NAME_FIELD));
            }
            if (subResult!= null && subResult.next()) {
                ((PersonalConversation)conversation).setParticipant2(subResult.getString(FIRST_NAME_FIELD) + " " + subResult.getString(LAST_NAME_FIELD));
            }

        }
        return conversation;
    }

    public Conversation getConversation(int idConversation) throws ClassNotFoundException, SQLException {
		ResultSet resultSet = query(String.format(QUERY_GET_CONVERSATIONS_FROM_ID,idConversation));
		Conversation conversation = null;
		
		if (resultSet != null && resultSet.next()) {
			conversation = getConversationByResultSet(resultSet);
		}
		return conversation;

    }
}

