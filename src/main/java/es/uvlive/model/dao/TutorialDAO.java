package es.uvlive.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import es.uvlive.model.Student;
import es.uvlive.model.Teacher;
import es.uvlive.model.Tutorial;
import es.uvlive.model.User;
import es.uvlive.utils.Logger;

public class TutorialDAO extends BaseDAO {
	
	private static final String QUERY_GET_USER_IDS = "SELECT * FROM " + CONVERSATION_ROL_UV_TABLE + " WHERE " + CONVERSATION_ID_TUTORIAL_FIELD + " = '%s'";
	
	// @Non-generated
	public Collection<es.uvlive.model.Tutorial> getTutorials() throws ClassNotFoundException, SQLException {
		String table = "Conversation";
		Collection<es.uvlive.model.Tutorial> tutorialsCollection = new ArrayList<es.uvlive.model.Tutorial>();
		
		String sqlQuery = "SELECT * FROM %s";
        Logger.put("Debug login: "+sqlQuery);
        ResultSet result = query(String.format(sqlQuery, table));
        if (result!=null) {
            try {
                while(result.next()) {
                	Tutorial tutorial = new Tutorial();
                    int idConversation = result.getInt("idConversation");
                    String  name = result.getString("name");
                    tutorial.setIdTutorial(idConversation);
                    tutorial.setName(name);
                    tutorialsCollection.add(tutorial);
                }
            } catch (SQLException ex) {
                Logger.put(this,"login - Error al recorrer el resultado de la consulta: " + ex.getMessage());
                return null;
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

