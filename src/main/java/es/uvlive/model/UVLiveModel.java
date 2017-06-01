package es.uvlive.model;

public class UVLiveModel {

	private TutorialCatalog tutorialCatalog;
	private SessionManager sessionManager;

	public void updateCourse() {
		// TODO - implement UVLiveModel.updateCourse
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param userName
	 * @param password
	 * @param loginType
	 */
	public String login(String userName, String password, String loginType) {
		// TODO - implement UVLiveModel.login
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param key
	 */
	public boolean containsUser(String key) {
		// TODO - implement UVLiveModel.containsUser
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param key
	 * @param idAlumno
	 */
	public void blockStudent(String key, int idAlumno) {
		// TODO - implement UVLiveModel.blockStudent
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param key
	 */
	private User getUser(String key) {
		// TODO - implement UVLiveModel.getUser
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idUser
	 */
	public User getUser(int idUser) {
		// TODO - implement UVLiveModel.getUser
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param key
	 * @param userName
	 */
	public boolean checkUserExists(String key, String userName) {
		// TODO - implement UVLiveModel.checkUserExists
		throw new UnsupportedOperationException();
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
	public boolean registerBusinessman(String key, String dni, String firstname, String lastname, String userName, String password) {
		// TODO - implement UVLiveModel.registerBusinessman
		throw new UnsupportedOperationException();
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

	/**
	 * 
	 * @param key
	 * @param String
	 */
	public void registerBroadcast(String key, broadcastText String) {
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