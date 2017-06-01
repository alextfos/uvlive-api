package es.uvlive.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import es.uvlive.model.Admin;
import es.uvlive.model.Student;
import es.uvlive.model.Teacher;
import es.uvlive.model.User;
import es.uvlive.utils.Logger;

public class UserDAO extends BaseDAO {
	// TODO @Non-generated (Hint: nothing on this class is from VP)
	
	/**
	 * 
	 * @param userName
	 * @param password
	 * @param loginType
	 */
	public User getUser(String userName, String password, String loginType) {
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
        }
        
        String sqlQuery = "SELECT * FROM "+table+" WHERE user = '"+userName+
                "' AND password = '"+password+"'";
        Logger.put("Debug login: "+sqlQuery);
        ResultSet result = query(sqlQuery);
        if(result!=null) {
            try {
                while(result.next()) {
                    String name = result.getString("user");
                    if(name.equals(userName)) {
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
                    	}
                    }
                }
            } catch (SQLException ex) {
                Logger.put(this,"login - Error al recorrer el resultado de la consulta: " + ex.getMessage());
                return null;
            }
        }
        return user;
	}

}