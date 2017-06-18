package es.uvlive.model;

import java.sql.SQLException;

import es.uvlive.exceptions.UserDefinedException;
import es.uvlive.exceptions.UserNotDefinedException;

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
	public void registerMerchant(String dni, String firstname, String lastname, String username, String password) throws ClassNotFoundException, SQLException, UserDefinedException {
		if (!checkUserExists(username)) {
			sessionManager.registerMerchant(dni,firstname,lastname,username,password);
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
	public void updateMerchant(String dni, String firstname, String lastname, String username, String password) throws ClassNotFoundException, SQLException, UserNotDefinedException {
		if (checkUserExists(username)) {
			sessionManager.updateMerchant(dni,firstname,lastname,username,password);
		} else {
			throw new UserNotDefinedException();
		}
	}

	public boolean checkUserExists(String userName) throws ClassNotFoundException, SQLException {
		return sessionManager.isUserExists(userName);
	}

}