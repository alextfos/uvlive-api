package es.uvlive.model;

import java.sql.SQLException;
import java.util.Collection;

import es.uvlive.model.dao.PermissionsDAO;
import es.uvlive.model.dao.UserDAO;

public class Teacher extends RolUV {
	
	public static final String LOGIN_TYPE = "Teacher";
	/**
	 * 
	 * @param student
	 */
	public boolean blockUser(Student student) {
		// TODO - implement Teacher.blockUser
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Calls PermissionsDAO method and changes student status to blocked
	 * @param idStudent
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void blockStudent(int idStudent) throws ClassNotFoundException, SQLException {
		new PermissionsDAO().bockStudent(idStudent);
	}
	
	/**
	 * Calls PermissionsDAO method and changes student status to unblocked
	 * @param idStudent
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void unblockStudent(int idStudent) throws ClassNotFoundException, SQLException {
		new PermissionsDAO().unblockStudent(idStudent);
	}

	@Override
	public Collection<RolUV> getUsers() throws ClassNotFoundException, SQLException {
		return new UserDAO().getStudents();
	}

}