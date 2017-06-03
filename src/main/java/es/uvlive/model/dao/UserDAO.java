package es.uvlive.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import es.uvlive.model.Admin;
import es.uvlive.model.Businessman;
import es.uvlive.model.Student;
import es.uvlive.model.Teacher;
import es.uvlive.model.User;
import es.uvlive.utils.Logger;

public class UserDAO extends BaseDAO {

	private static final String QUERY_LOGIN = "SELECT * FROM %s WHERE user = '%s' AND password = '%s'";
	
	// TODO @Non-generated (Hint: nothing on this class is from VP)
	/**
	 * 
	 * @param userName
	 * @param password
	 * @param loginType
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public User getUser(String userName, String password, String loginType) throws SQLException, ClassNotFoundException {
		User user = null;
		String table = "";
        switch(loginType) {
            case "Alumno":
                table = STUDENT_TABLE;
                break;
            case "Profesor":
                table = TEACHER_TABLE;
                break;
            case "Admin":
                table = ADMINISTRATOR_TABLE;
                break;
            case "Businessman":
            	table = BUSINESSMAN_TABLE;
            	break;
            default:
            	return null;
        }
        
        String sqlQuery = String.format(QUERY_LOGIN, table,userName,password);
        ResultSet result = query(sqlQuery);
        if (result!=null) {
            while (result.next()) {
                String name = result.getString("user");
                if (name.equals(userName)) {
                	switch (loginType) {
                	case "Alumno":
                        user = new Student();
                        break;
                    case "Profesor":
                        user = new Teacher();
                        break;
                    case "Admin":
                        user = new Admin();
                        break;
                    case "Businessman":
                    	user = new Businessman();
                	}
                	user.setUserId(String.valueOf(result.getInt("id" + table)));
                	// Missing firstname and lastname
                }
            }
        }
        return user;
	}

}