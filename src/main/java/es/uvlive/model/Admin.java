package es.uvlive.model;

public class Admin extends User {
	
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
	 */
	public void registerBusinessman(String dni, String firstname, String lastname, String username, String password) {
		// TODO - implement Admin.registerBusinessman
		sessionManager.registerBusinessman(dni,firstname,lastname,username,password);
	}
	
	/**
	 * 
	 * @param dni
	 * @param firstname
	 * @param lastname
	 * @param username
	 * @param password
	 */
	public void updateBusinessman(String dni, String firstname, String lastname, String username, String password) {
		// TODO - implement Admin.registerBusinessman
		sessionManager.updateBusinessman(dni,firstname,lastname,username,password);
	}

	public boolean checkUserExists(String userName) {
		return sessionManager.isUserExists(userName);
	}

}