package es.uvlive.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import es.uvlive.api.GoogleInterface;
import es.uvlive.api.RetrofitFactory;
import es.uvlive.api.requests.Notification;
import es.uvlive.api.requests.NotificationRequest;
import es.uvlive.api.response.NotificationResponse;
import es.uvlive.controllers.form.GetMessagesForm;
import es.uvlive.exceptions.ConversationNotCreatedException;
import es.uvlive.exceptions.UnauthorizedException;
import es.uvlive.model.dao.BroadcastDAO;
import es.uvlive.utils.Logger;
import es.uvlive.utils.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
	public void registerMerchant(String key, String dni, String firstname, String lastname, String userName,
								 String password) throws Exception {
		User user = getUser(key);
		
		if (user != null && user instanceof Admin) {
			((Admin)user).registerMerchant(dni, firstname, lastname, userName, password);
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
	public void updateMerchant(String key, String dni, String firstname, String lastname, String userName,
                               String password) throws Exception {
		User user = getUser(key);
		
		if (user != null && user instanceof Admin) {
			((Admin)user).updateMerchant(dni, firstname, lastname, userName, password);
		} else {
			throw new UnauthorizedException();
		}
		
	}

	// TODO Check in VP (Hint: broadcastText String)
	/**
	 * Registers Broadcast requested by Merchant
	 * @param key
	 * @param String
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void registerBroadcast(String key, String title, String broadcastText) throws Exception {
		User user = getUser(key);
		if (user != null && user instanceof Merchant) {
			((Merchant)user).registerBroadcast(title, broadcastText);
		}
	}

	/**
	 * 
	 * @param text
	 */
	public void sendBroadcasts() throws SQLException, ClassNotFoundException {
		List<Broadcast> broadcastList = new BroadcastDAO().getBroadcasts();
		List<User> users = sessionManager.getUsers();
		for (final User user: users) {
			if (user instanceof RolUV) {
				String pushToken = ((RolUV)user).getPushToken();
				for (Broadcast broadcast:broadcastList) {
					GoogleInterface googleInterface = RetrofitFactory.getGoogleInterface();
					NotificationRequest notificationRequest = new NotificationRequest();

					notificationRequest.setNotification(new Notification(broadcast.getTitle(), broadcast.getText()));
					notificationRequest.setTo(pushToken);
					Call<NotificationResponse> callback = googleInterface.sendNotification(notificationRequest);
					callback.enqueue(new Callback<NotificationResponse>() {
						@Override
						public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> rspns) {
							NotificationResponse response = rspns.body();
							if (response.getFailure() > 0) {
								try {
									((RolUV)user).removePushToken();
								} catch (Exception e) {
									e.printStackTrace();
									Logger.putError(UVLiveModel.this, e);
								}
							}
						}

						@Override
						public void onFailure(Call<NotificationResponse> call, Throwable thrwbl) {
							System.out.println("Error: ");
							thrwbl.printStackTrace();
						}
					});
				}
			}
		}

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
				return ((RolUV)user).getUsers();
		} else {
			throw new UnauthorizedException();
		}
	}
	
	public void initConversation(String token, int userId) throws Exception {
		User user = getUser(token);
		if (user != null && user instanceof RolUV) {
			try {
				((RolUV)user).initConversation(userId);
			} catch(Exception e) {
				e.printStackTrace();
				throw new ConversationNotCreatedException();
			}
		} else {
			throw new UnauthorizedException();
		}
	}

}