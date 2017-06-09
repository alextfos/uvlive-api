package es.uvlive.model;

import java.sql.SQLException;
import java.util.Collection;

import es.uvlive.model.dao.UserDAO;

public class Student extends RolUV {
	
	public static final String LOGIN_TYPE = "Student";
	
	// TODO @Non-generated
	private boolean blocked;

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	@Override
	public Collection<RolUV> getUsers() throws ClassNotFoundException, SQLException {
		return new UserDAO().getTeachers();
	}
	
}