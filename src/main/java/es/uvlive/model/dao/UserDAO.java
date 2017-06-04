package es.uvlive.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import es.uvlive.controllers.exceptions.WrongCredentialsException;
import es.uvlive.model.Admin;
import es.uvlive.model.Businessman;
import es.uvlive.model.RolUV;
import es.uvlive.model.Student;
import es.uvlive.model.Teacher;
import es.uvlive.model.User;
import es.uvlive.utils.Logger;

public class UserDAO extends BaseDAO {

	private static final String QUERY_LOGIN_USER = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_NAME_FIELD + " = '%s' AND password = '%s'";
	private static final String QUERY_LOGIN_ROL_UV = "SELECT * FROM " + ROL_UV_TABLE + " WHERE " + USER_ID_FIELD + " = '%d'";
	private static final String QUERY_LOGIN_STUDENT = "SELECT * FROM " + STUDENT_TABLE + " WHERE " + USER_ID_FIELD + " = '%d'";
	private static final String QUERY_LOGIN_TEACHER = "SELECT * FROM " + TEACHER_TABLE + " WHERE " + USER_ID_FIELD + " = '%d'";
	private static final String QUERY_LOGIN_ADMIN = "SELECT * FROM " + ADMIN_TABLE + " WHERE " + USER_ID_FIELD + " = '%d'";
	private static final String QUERY_LOGIN_BUSINESSMAN = "SELECT * FROM " + BUSINESSMAN_TABLE + " WHERE " + USER_ID_FIELD + " = '%d'";
	
	// TODO @Non-generated (Hint: nothing on this class is from VP)
	/**
	 * 
	 * @param userName
	 * @param password
	 * @param loginType
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws WrongCredentialsException 
	 */
	public User getUser(String userName, String password, String loginType) throws SQLException, ClassNotFoundException, WrongCredentialsException {
		User user = null;
        
        String sqlQuery = String.format(QUERY_LOGIN_USER,userName,password);
        ResultSet result = query(sqlQuery);
        if (result!=null) {
            if (result.next()) {
                String name = result.getString(USER_NAME_FIELD);
                if (name.equals(userName)) {
                	int userId = result.getInt(USER_ID_FIELD);
                	
                	switch (loginType) {
                	case Student.LOGIN_TYPE:
                        user = new Student();
                        user.setUserId(userId);
                        getRolUvData((RolUV) user);
                        getStudentData((Student)user);
                        break;
                    case Teacher.LOGIN_TYPE:
                        user = new Teacher();
                        user.setUserId(userId);
                        getRolUvData((RolUV)user);
                        getTeacherData((Teacher)user);
                        break;
                    case Admin.LOGIN_TYPE:
                        user = new Admin();
                        user.setUserId(userId);
                        getAdminData((Admin)user);
                        break;
                    case Businessman.LOGIN_TYPE:
                    	user = new Businessman();
                    	user.setUserId(userId);
                    	getBusinessmanData((Businessman)user);
                	default:
                		return null;
                	}
                	user.setFirstname(result.getString(FIRST_NAME_FIELD));
                	user.setLastname(result.getString(LAST_NAME_FIELD));
                }
            }
        }
        return user;
	}
	
	protected void getRolUvData(RolUV user) throws ClassNotFoundException, SQLException, WrongCredentialsException {
		String sqlQuery = String.format(QUERY_LOGIN_ROL_UV,user.getUserId());
		ResultSet result = query(sqlQuery);
        if (result!=null && result.next()) {
            user.setPushToken(result.getString(PUSH_TOKEN_FIELD));
        } else {
        	throw new WrongCredentialsException();
        }
	}
	
	protected void getBusinessmanData(Businessman user) throws ClassNotFoundException, SQLException, WrongCredentialsException {
		String sqlQuery = String.format(QUERY_LOGIN_BUSINESSMAN,user.getUserId());
		ResultSet result = query(sqlQuery);
        if (result!=null && result.next()) {
            user.setDni(result.getString(DNI_FIELD));
        } else {
        	throw new WrongCredentialsException();
        }
	}
	
	protected void getAdminData(Admin user) throws SQLException, WrongCredentialsException, ClassNotFoundException {
		String sqlQuery = String.format(QUERY_LOGIN_ADMIN,user.getUserId());
		ResultSet result = query(sqlQuery);
        if (!(result!=null && result.next())) {
        	throw new WrongCredentialsException();
        }
	}
	
	protected void getTeacherData(Teacher user) throws ClassNotFoundException, SQLException, WrongCredentialsException {
		String sqlQuery = String.format(QUERY_LOGIN_TEACHER,user.getUserId());
		ResultSet result = query(sqlQuery);
        if (!(result!=null && result.next())) {
        	throw new WrongCredentialsException();
        }
	}
	
	protected void getStudentData(Student user) throws ClassNotFoundException, SQLException, WrongCredentialsException {
		String sqlQuery = String.format(QUERY_LOGIN_STUDENT,user.getUserId());
		ResultSet result = query(sqlQuery);
        if (result!=null && result.next()) {
            user.setBlocked(result.getInt(BLOCKED_FIELD) != 0);
        } else {
        	throw new WrongCredentialsException();
        }
	}

}