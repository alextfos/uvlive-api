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
		String table = "";
		String userType = "";
		ArrayList<Integer> idsOfTutorialsCollection = new ArrayList<Integer>();
		
		if (user instanceof Student) {
			table = CONVERSATION_STUDENT_TABLE;
			userType = STUDENT_TABLE;
		} else if (user instanceof Teacher){
			table = CONVERSATION_TEACHER_TABLE;
			userType = TEACHER_TABLE;
		}
		if (table != "" && userType != "") {
			String idUserTableColumnName = userType + "id" + userType;
			String sqlQuery = "SELECT * FROM %s where %s = '%s'";
	        Logger.put("Debug login: "+sqlQuery);
	        
	        ArrayList<String> params = new ArrayList<String>();
	        params.add(table);
	        params.add(idUserTableColumnName); /*column name of row depending on if user is a teacher or a student*/
	        params.add(user.getUserId());
	        
	        ResultSet result = query(String.format(sqlQuery, table, idUserTableColumnName, String.valueOf(user.getUserId())));
	        if (result != null) {
                while(result.next()) {
                	// TODO check DB (Hint: column names)
                    int idConversation = result.getInt("ConversationidConversation");
                    idsOfTutorialsCollection.add(idConversation);
                }
	        }
		}
        return idsOfTutorialsCollection;
	}

}

