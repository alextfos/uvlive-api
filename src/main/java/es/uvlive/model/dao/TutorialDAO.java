package es.uvlive.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import es.uvlive.model.Message;
import es.uvlive.model.Student;
import es.uvlive.model.Teacher;
import es.uvlive.model.Tutorial;
import es.uvlive.model.User;
import es.uvlive.utils.Logger;

public class TutorialDAO extends BaseDAO {
	
	private static final String QUERY_GET_USER_IDS = "SELECT * FROM " + CONVERSATION_ROL_UV_TABLE + " WHERE " + ROL_UV_ID_USER_FIELD + " = '%s'";
	private static final String QUERY_GET_TUTORIALS = "SELECT * FROM " + CONVERSATION_TABLE;
	
	// @Non-generated
	public Collection<es.uvlive.model.Tutorial> getTutorials() throws ClassNotFoundException, SQLException {
		Collection<es.uvlive.model.Tutorial> tutorialsCollection = new ArrayList<es.uvlive.model.Tutorial>();
		
        ResultSet result = query(QUERY_GET_TUTORIALS);
        if (result!=null) {
            while(result.next()) {
            	Tutorial tutorial = new Tutorial();
                int idConversation = result.getInt(TUTORIAL_ID_FIELD);
                String  name = result.getString(NAME_FIELD);
                tutorial.setIdTutorial(idConversation);
                tutorial.setName(name);
                tutorial.setMessages(new ArrayList<Message>());
                tutorialsCollection.add(tutorial);
            }
        }
        return tutorialsCollection;
	}
	
	// @Non-generated
	public ArrayList<Integer> getIdsOfUserTutorials (User user) throws SQLException, ClassNotFoundException {
		
		ArrayList<Integer> idsOfTutorialsCollection = new ArrayList<Integer>();
			
        ResultSet result = query(String.format(QUERY_GET_USER_IDS, String.valueOf(user.getUserId())));
        if (result != null) {
            while(result.next()) {
                int idConversation = result.getInt(CONVERSATION_ID_TUTORIAL_FIELD);
                idsOfTutorialsCollection.add(idConversation);
            }
        }
        return idsOfTutorialsCollection;
	}

}

