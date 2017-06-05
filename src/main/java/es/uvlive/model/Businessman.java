package es.uvlive.model;

import java.sql.SQLException;

import es.uvlive.model.dao.BusinessmanDAO;

public class Businessman extends User {
	
	public static final String LOGIN_TYPE = "Businessman";
	
	// @Non-generated
	private String dni;

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public void registerBroadcast(String broadcastText) throws ClassNotFoundException, SQLException {
		new BusinessmanDAO().registerBroadcast(this, broadcastText);
	}
	
}
