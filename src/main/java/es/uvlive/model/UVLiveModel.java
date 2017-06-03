package es.uvlive.model;

import java.util.Collection;

public class UVLiveModel {

	// TODO @Non-generated
	private static UVLiveModel sUVLiveModel;

	private TutorialCatalog tutorialCatalog;
	private SessionManager sessionManager;

	public static UVLiveModel getInstance() {
		if (sUVLiveModel == null) {
			sUVLiveModel = new UVLiveModel();
		}
		return sUVLiveModel;
	}

	// TODO @Non-generated method
	public UVLiveModel() {
		tutorialCatalog = new TutorialCatalog();
		sessionManager = new SessionManager();
	}

	public void updateCourse() {
		// TODO - implement UVLiveModel.actualizarCurso
		throw new UnsupportedOperationException();
	}

	// TODO @Non-generated method
	/**
	 * 
	 * @param userName
	 * @param password
	 * @param loginType
	 */
	public String login(String userName, String password, String loginType, String key) throws Exception {
		return sessionManager.login(userName, password, loginType, key);
	}

	// TODO @Non-generated method
	/**
	 * Logout
	 * 
	 * @param token
	 */
	public void logout(String token) {
		sessionManager.logout(token);
	}

	/**
	 * 
	 * @param key
	 */
	public boolean containsUser(String key) {
		// TODO - implement UVLiveModel.containsUser
		throw new UnsupportedOperationException();
	}

	// TODO Check in VP (Hint: idAlumno)
	/**
	 * 
	 * @param key
	 * @param idAlumno
	 */
	public void blockStudent(String key, int idAlumno) {
		// TODO - implement UVLiveModel.banearAlumno
		throw new UnsupportedOperationException();
	}

	// TODO Check in VP (Hint: No method with different name and same parameters
	// was found)
	/**
	 * 
	 * @param key
	 * @param traderName
	 */
	public void validarNombreComerciante(String key, String traderName) {
		// TODO - implement UVLiveModel.validarNombreComerciante
		throw new UnsupportedOperationException();
	}

	// TODO Check in VP (Hint: integer user id)
	/**
	 * 
	 * @param idUser
	 */
	public User getUser(int idUser) {
		// TODO - implement UVLiveModel.getUser
		throw new UnsupportedOperationException();
	}

	// TODO @Non-generated
	/**
	 * Gets User by session token
	 * 
	 * @param key
	 * @return logged User
	 */
	public User getUser(String key) {
		return sessionManager.getUser(key);
	}

	/**
	 * 
	 * @param key
	 * @param userName
	 */
	public boolean checkUserExists(String key, String userName) {
		User user = getUser(key);
		if (user != null && user instanceof Admin) {
			return ((Admin) user).checkUserExists(userName);
		} else {
			return false; // TODO throw Not Authorized Exception
		}
	}

	/**
	 * 
	 * @param key
	 * @param dni
	 * @param firstname
	 * @param lastname
	 * @param userName
	 * @param password
	 */
	public boolean registerBusinessman(String key, String dni, String firstname, String lastname, String userName,
			String password) {
		User user = getUser(key);
		
		if (user instanceof Admin && !((Admin)user).checkUserExists(userName)) {
			((Admin)user).registerBusinessman(dni, firstname, lastname, userName, password);
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param key
	 * @param dni
	 * @param firstname
	 * @param lastname
	 * @param userName
	 * @param password
	 */
	public boolean updateBusinessman(String key, String dni, String firstname, String lastname, String userName,
			String password) {
		User user = getUser(key);
		
		if (user instanceof Admin && !((Admin)user).checkUserExists(userName)) {
			((Admin)user).updateBusinessman(dni, firstname, lastname, userName, password);
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param key
	 * @param idTutorial
	 * @param text
	 */
	public void sendMessage(String key, int idTutorial, String text) {
		// TODO - implement UVLiveModel.sendMessage
		throw new UnsupportedOperationException();
	}

	// TODO Check in VP (Hint: broadcastText String)
	/**
	 * 
	 * @param key
	 * @param String
	 */
	public void registerBroadcast(String key, String broadcastText) {
		// TODO - implement UVLiveModel.registerBroadcast
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param text
	 */
	public void sendBroadcast(String text) {
		// TODO - implement UVLiveModel.sendBroadcast
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param key
	 */
	public Collection<es.uvlive.model.Tutorial> getTutorials(String key) {
		// TODO - implement UVLiveModel.getTutorials
		throw new UnsupportedOperationException();
	}

}