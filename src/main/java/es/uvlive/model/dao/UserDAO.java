package es.uvlive.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import es.uvlive.exceptions.WrongCredentialsException;
import es.uvlive.model.*;
import es.uvlive.model.Merchant;

public class UserDAO extends BaseDAO {

	private static final String QUERY_GET_USER = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_NAME_FIELD + " ='%s'";
	private static final String QUERY_LOGIN_USER = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_NAME_FIELD + " = '%s' AND password = '%s'";
	private static final String QUERY_LOGIN_ROL_UV = "SELECT * FROM " + ROL_UV_TABLE + " WHERE " + USER_ID_FIELD + " = '%d'";
	private static final String QUERY_LOGIN_STUDENT = "SELECT * FROM " + STUDENT_TABLE + " WHERE " + USER_ID_FIELD + " = '%d'";
	private static final String QUERY_LOGIN_TEACHER = "SELECT * FROM " + TEACHER_TABLE + " WHERE " + USER_ID_FIELD + " = '%d'";
	private static final String QUERY_LOGIN_ADMIN = "SELECT * FROM " + ADMIN_TABLE + " WHERE " + USER_ID_FIELD + " = '%d'";
	private static final String QUERY_LOGIN_MERCHANT = "SELECT * FROM " + MERCHANT_TABLE + " WHERE " + USER_ID_FIELD + " = '%d'";
	
	private static final String QUERY_SAVE_PUSH_TOKEN = "UPDATE " + ROL_UV_TABLE + " SET " + PUSH_TOKEN_FIELD + " = '%s' WHERE " + USER_ID_FIELD + "= %d";
	
	private static final String QUERY_STUDENTS_USERS = "SELECT * FROM " + STUDENT_TABLE + " left join " + USER_TABLE + " on " + STUDENT_TABLE + "." + USER_ID_FIELD + " = " + USER_TABLE + "." + USER_ID_FIELD + ";";
	
	private static final String QUERY_TEACHERS_USERS = "SELECT * FROM " + TEACHER_TABLE + " left join " + USER_TABLE + " on " + TEACHER_TABLE + "." + USER_ID_FIELD + " = " + USER_TABLE + "." + USER_ID_FIELD + ";";

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
                        user.setUsername(name);
                        getRolUvData((RolUV) user);
                        getStudentData((Student)user);
                        break;
                    case Teacher.LOGIN_TYPE:
                        user = new Teacher();
                        user.setUserId(userId);
                        user.setUsername(name);
                        getRolUvData((RolUV)user);
                        getTeacherData((Teacher)user);
                        break;
                    case Admin.LOGIN_TYPE:
                        user = new Admin();
                        user.setUserId(userId);
                        user.setUsername(name);
                        getAdminData((Admin)user);
                        break;
                    case Merchant.LOGIN_TYPE:
                    	user = new Merchant();
                    	user.setUserId(userId);
                    	user.setUsername(name);
                    	getMerchantData((Merchant)user);
                    	break;
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

	public User getUser(String userName) throws ClassNotFoundException, SQLException {
		User user = null;
		ResultSet result = query(String.format(QUERY_GET_USER,userName));

		if (result != null) {
			if (result.next()) {
				user = new User();
				user.setUsername(result.getString(USER_NAME_FIELD));
				user.setFirstname(result.getString(FIRST_NAME_FIELD));
				user.setLastname(result.getString(LAST_NAME_FIELD));
			}
		}

		return user;
	}
	
	public void savePushToken(int userId, String pushToken) throws SQLException {
		update(String.format(QUERY_SAVE_PUSH_TOKEN, pushToken,userId));
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
	
	protected void getMerchantData(Merchant user) throws ClassNotFoundException, SQLException, WrongCredentialsException {
		String sqlQuery = String.format(QUERY_LOGIN_MERCHANT,user.getUserId());
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

	public Collection<RolUV> getStudents() throws ClassNotFoundException, SQLException {
		Collection<RolUV> students = new ArrayList<RolUV>();
        ResultSet result = query(QUERY_STUDENTS_USERS);
        if (result!=null) {
            if (result.next()) {
				Student student = new Student();
				student.setUserId(result.getInt(USER_ID_FIELD));
				student.setFirstname(result.getString(FIRST_NAME_FIELD));
				student.setLastname(result.getString(LAST_NAME_FIELD));
				((User)student).setUsername(result.getString(USER_NAME_FIELD));
				students.add(student);
            }
        }
        return students;
	}

	public Collection<RolUV> getTeachers() throws ClassNotFoundException, SQLException {
		Collection<RolUV> teachers = new ArrayList<RolUV>();
        ResultSet result = query(QUERY_TEACHERS_USERS);
        if (result!=null) {
            if (result.next()) {
				Teacher teacher = new Teacher();
				teacher.setUserId(result.getInt(USER_ID_FIELD));
				teacher.setFirstname(result.getString(FIRST_NAME_FIELD));
				teacher.setLastname(result.getString(LAST_NAME_FIELD));
				((User)teacher).setUsername(result.getString(USER_NAME_FIELD));
				teachers.add(teacher);
            }
        }
		return teachers;
	}
	
	

}