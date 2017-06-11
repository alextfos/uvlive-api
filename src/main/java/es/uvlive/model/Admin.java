package es.uvlive.model;

import java.sql.SQLException;

import es.uvlive.controllers.exceptions.UserDefinedException;
import es.uvlive.controllers.exceptions.UserNotDefinedException;

public class Admin extends User {
	
	public static final String LOGIN_TYPE = "Admin";
	
	private SessionManager sessionManager;
	
	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	/**
	 * 
	 * @param dni
	 * @param firstname
	 * @param lastname
	 * @param username
	 * @param password
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws UserDefinedException 
	 */
	public void registerBusinessman(String dni, String firstname, String lastname, String username, String password) throws ClassNotFoundException, SQLException, UserDefinedException {
		if (!checkUserExists(username)) {
			sessionManager.registerBusinessman(dni,firstname,lastname,username,password);
		} else {
			throw new UserDefinedException();
		}
	}
	
	/**
	 * 
	 * @param dni
	 * @param firstname
	 * @param lastname
	 * @param username
	 * @param password
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws UserNotDefinedException 
	 */
	public void updateBusinessman(String dni, String firstname, String lastname, String username, String password) throws ClassNotFoundException, SQLException, UserNotDefinedException {
		if (checkUserExists(username)) {
			sessionManager.updateBusinessman(dni,firstname,lastname,username,password);
		} else {
			throw new UserNotDefinedException();
		}
	}

	public boolean checkUserExists(String userName) throws ClassNotFoundException, SQLException {
		return sessionManager.isUserExists(userName);
	}

}