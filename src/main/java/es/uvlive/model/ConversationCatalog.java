package es.uvlive.model;

import java.sql.SQLException;
import java.util.*;

import es.uvlive.exceptions.ConversationNotCreatedException;
import es.uvlive.model.dao.ConversationDAO;

public class ConversationCatalog {
	private Collection<Conversation> conversations;

	public ConversationCatalog() {
		try {
			conversations = new ConversationDAO().getConversations();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error intializing ConversationCatalog: " + e.getMessage());
		}
	}

	public Collection<Conversation> getConversations(User user) throws SQLException, ClassNotFoundException {
		Collection<Conversation> resultConversations = new ArrayList<Conversation>();
		ArrayList<Integer> idsOfConversationCollection = new ConversationDAO().getIdsOfUserConversations(user);
		for (Conversation conversation : conversations) {
			for (Integer idConversation : idsOfConversationCollection ) {
				if (conversation.getIdConversation() == idConversation) {
					resultConversations.add(conversation);
				}
			}
		}
		
		return resultConversations;
	}
	
	public Conversation addAndGetConversation(int idConversation) throws ClassNotFoundException, SQLException, ConversationNotCreatedException {
		Conversation conversation = new ConversationDAO().getConversation(idConversation);
		if (conversation != null) {
			conversations.add(conversation);
		} else {
			throw new ConversationNotCreatedException();
		}
		return conversation;
	}
	
}