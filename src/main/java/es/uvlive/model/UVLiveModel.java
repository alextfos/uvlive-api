package es.uvlive.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import es.uvlive.controllers.form.GetMessagesForm;
import es.uvlive.exceptions.UnauthorizedException;
import es.uvlive.exceptions.UserDefinedException;
import es.uvlive.utils.StringUtils;

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
    public String login(String userName, String password, String loginType, String pushToken, String key) throws Exception {
        String token = sessionManager.login(userName, password, loginType, pushToken, key);
        if (!StringUtils.isEmpty(token)) {
        	User user = sessionManager.getUser(token);
        	if (user != null && user instanceof RolUV) {
        		((RolUV)user).setTutorialsCatalog(this.tutorialCatalog);
        		((RolUV)user).init();
        	}
        }
        return token;
    }
    
    // TODO @Non-generated method
    /**
     * Logout
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

    /**
     * 
     * @param key
     * @param Student
     * @throws Exception 
     */
    // TODO check names in VP (Hint: idAlumno)
	public void blockStudent(String key, int idStudent) throws Exception {
		User user = getUser(key);
		if (user != null && user instanceof Teacher) {
			((Teacher)user).blockStudent(idStudent);
			sessionManager.blockUser(idStudent);
		} else {
			throw new UnauthorizedException();
		}
	}
	
    /**
     * 
     * @param key
     * @param Student
     * @throws Exception 
     */
    // TODO @Non-generated
	public void unblockStudent(String key, int idStudent) throws Exception {
		User user = getUser(key);
		if (user != null && user instanceof Teacher) {
			((Teacher)user).unblockStudent(idStudent);
			sessionManager.unblockUser(idStudent);
		} else {
			throw new UnauthorizedException();
		}
	}

	// TODO @Non-generated
	/**
	 * Gets User by session token
	 * 
	 * @param key
	 * @return logged User
	 */
	public User getUser(String key) throws Exception {
		return sessionManager.getUser(key);
	}

	/**
	 * 
	 * @param key
	 * @param userName
	 */
	public boolean checkUserExists(String key, String userName) throws Exception {
		User user = getUser(key);
		if (user != null && user instanceof Admin) {
			return ((Admin) user).checkUserExists(userName);
		} else {
			throw new UnauthorizedException();
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
	public void registerBusinessman(String key, String dni, String firstname, String lastname, String userName,
			String password) throws Exception {
		User user = getUser(key);
		
		if (user != null && user instanceof Admin) {
			((Admin)user).registerBusinessman(dni, firstname, lastname, userName, password);
		} else {
			throw new UnauthorizedException();
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
	public void updateBusinessman(String key, String dni, String firstname, String lastname, String userName,
			String password) throws Exception {
		User user = getUser(key);
		
		if (user != null && user instanceof Admin) {
			((Admin)user).updateBusinessman(dni, firstname, lastname, userName, password);
		} else {
			throw new UnauthorizedException();
		}
		
	}

	// TODO Check in VP (Hint: broadcastText String)
	/**
	 * Registers Broadcast requested by Businessman
	 * @param key
	 * @param String
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void registerBroadcast(String key, String broadcastText) throws Exception {
		User user = getUser(key);
		if (user != null && user instanceof Businessman) {
			((Businessman)user).registerBroadcast(broadcastText);
		}
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
	public Collection<es.uvlive.model.Tutorial> getTutorials(String key) throws Exception {
		// TODO @Non-generated
		Collection <es.uvlive.model.Tutorial> tutorials = new ArrayList<es.uvlive.model.Tutorial>();
		User user = getUser(key);
		if (user != null && user instanceof RolUV) {
			((RolUV) user).setTutorialsCatalog(this.tutorialCatalog);
			tutorials = ((RolUV) user).getTutorials();
		} else {
			throw new UnauthorizedException();
		}
		
		return tutorials;
	}
	
	/**
	 * 
	 * @param key
	 * @param idTutorial
	 * @param text
	 */
	public void sendMessage(String key, int idTutorial, String text) throws Exception {
		User user = getUser(key);
		if (user != null && user instanceof RolUV) {
			((RolUV)user).sendMessage(idTutorial, text);
		} else {
			throw new UnauthorizedException();
		}
	}

	public Collection<Message> getMessages(String key, int idConversation) throws Exception {
		Collection<Message> messages = new ArrayList<Message>();
		User user = getUser(key);
		if (user != null && user instanceof RolUV) {
			// Only RolUV users can send messages
			messages = ((RolUV)user).getMessages(idConversation);
		} else {
			throw new UnauthorizedException();
		}
		return messages;
	}
	
	public Collection<Message> getPreviousMessages(String key, GetMessagesForm getMessagesForm) throws Exception {
		Collection<Message> messages = new ArrayList<Message>();
		User user = getUser(key);
		if (getMessagesForm.getIdMessage() > 0 & getMessagesForm.getIdConversation() > 0) {
			if (user != null && user instanceof RolUV) {
				// Only RolUV users can send messages
				messages = ((RolUV)user).getPreviousMessages(getMessagesForm.getIdConversation(), getMessagesForm.getIdMessage());
			} else {
				throw new UnauthorizedException();
			}
		}
		return messages;
	}
	
	public Collection<Message> getFollowingMessages(String key, GetMessagesForm getMessagesForm) throws Exception {
		Collection<Message> messages = new ArrayList<Message>();
		User user = getUser(key);
		if (getMessagesForm.getIdMessage() > 0 & getMessagesForm.getIdConversation() > 0) {
			if (user != null && user instanceof RolUV) {
				// Only RolUV users can send messages
				messages = ((RolUV)user).getFollowingMessages(getMessagesForm.getIdConversation(), getMessagesForm.getIdMessage());
			} else {
				throw new UnauthorizedException();
			}
		}
		return messages;
	}
	
	// @Non-generated
	public void updateToken(String key, String pushToken) throws Exception {
		User user = getUser(key);
		
		if (user != null && user instanceof RolUV) {
			((RolUV)user).setPushToken(pushToken);
		} else {
			throw new UnauthorizedException();
		}
	}

	public <T extends RolUV> Collection<RolUV> getUsers(String token) throws Exception {
		User user = getUser(token);
		if (user != null && user instanceof RolUV) {
			if (user instanceof Teacher) {
				return ((Teacher)user).getUsers();
			} else if (user instanceof Student) {
				return ((Student)user).getUsers();
			}
		} else {
			throw new UnauthorizedException();
		}
		return null;
	}

}