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
import es.uvlive.model.dao.MessageDAO;
import es.uvlive.utils.Logger;
import es.uvlive.utils.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UVLiveModel {
	
	public static final int PAGE_SIZE = 10;
	public static final int BUFFER_SIZE = 2;
	public static final int LIST_SIZE = 10;

	private static UVLiveModel sUVLiveModel;

	private ConversationCatalog conversationCatalog;
	private SessionManager sessionManager;

	public static UVLiveModel getInstance() {
		if (sUVLiveModel == null) {
			sUVLiveModel = new UVLiveModel();
		}
		return sUVLiveModel;
	}

	public UVLiveModel() {
		conversationCatalog = new ConversationCatalog();
		sessionManager = new SessionManager();
		Message.initMessageId(new MessageDAO().getMaxMessageId());
	}

    /**
     * 
     * @param userName
     * @param password
     * @param loginType
     */
    public synchronized String login(String userName, String password, String loginType, String pushToken, String key) throws Exception {
        String token = sessionManager.login(userName, password, loginType, pushToken, key);
        if (!StringUtils.isEmpty(token)) {
        	User user = sessionManager.getUser(token);
        	if (user != null && user instanceof RolUV) {
        		((RolUV)user).setConversationCatalog(this.conversationCatalog);
        		((RolUV)user).init();
        	}
        }
        return token;
    }

    /**
     * Logout
     * @param token
     */
    public synchronized void logout(String token) {
        sessionManager.logout(token);
    }

    /**
     * 
     * @param key
     * @param Student
     * @throws Exception 
     */
	public synchronized void blockStudent(String key, int idStudent) throws Exception {
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
	public synchronized void unblockStudent(String key, int idStudent) throws Exception {
		User user = getUser(key);
		if (user != null && user instanceof Teacher) {
			((Teacher)user).unblockStudent(idStudent);
		} else {
			throw new UnauthorizedException();
		}
	}

	/**
	 * Gets User by session token
	 * 
	 * @param key
	 * @return logged User
	 */
	public synchronized User getUser(String key) throws Exception {
		return sessionManager.getUser(key);
	}

	/**
	 * 
	 * @param key
	 * @param userName
	 */
	public synchronized boolean checkUserExists(String key, String userName) throws Exception {
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
	public synchronized void registerMerchant(String key, String dni, String firstname, String lastname, String userName,
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
	public synchronized void updateMerchant(String key, String dni, String firstname, String lastname, String userName,
                               String password) throws Exception {
		User user = getUser(key);
		
		if (user != null && user instanceof Admin) {
			((Admin)user).updateMerchant(dni, firstname, lastname, userName, password);
		} else {
			throw new UnauthorizedException();
		}
		
	}

	/**
	 * Registers Broadcast requested by Merchant
	 * @param key
	 * @param String
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public synchronized void registerBroadcast(String key, String title, String broadcastText) throws Exception {
		User user = getUser(key);
		if (user != null && user instanceof Merchant) {
			((Merchant)user).registerBroadcast(title, broadcastText);
		}
	}

	/**
	 * 
	 * @param text
	 */
	public synchronized void sendBroadcasts() throws SQLException, ClassNotFoundException {
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
	public synchronized Collection<Conversation> getConversations(String key) throws Exception {
		Collection <Conversation> conversations = new ArrayList<Conversation>();
		User user = getUser(key);
		if (user != null && user instanceof RolUV) {
			((RolUV) user).setConversationCatalog(this.conversationCatalog);
			conversations = ((RolUV) user).getConversations();
		} else {
			throw new UnauthorizedException();
		}
		
		return conversations;
	}
	
	/**
	 * 
	 * @param key
	 * @param idConversation
	 * @param text
	 */
	public synchronized void sendMessage(String key, int idConversation, String text) throws Exception {
		User user = getUser(key);
		if (user != null && user instanceof RolUV) {
			((RolUV)user).sendMessage(idConversation, text);
		} else {
			throw new UnauthorizedException();
		}
	}

	public synchronized Collection<Message> getMessages(String key, int idConversation) throws Exception {
		Collection<Message> messages;
		User user = getUser(key);
		if (user != null && user instanceof RolUV) {
			// Only RolUV users can send messages
			messages = ((RolUV)user).getMessages(idConversation);
		} else {
			throw new UnauthorizedException();
		}
		return messages;
	}
	
	public synchronized Collection<Message> getPreviousMessages(String key, GetMessagesForm getMessagesForm) throws Exception {
		Collection<Message> messages;
		User user = getUser(key);
		if (user != null && user instanceof RolUV) {
			messages = ((RolUV)user).getPreviousMessages(getMessagesForm.getIdConversation(), getMessagesForm.getIdMessage());
		} else {
			throw new UnauthorizedException();
		}
		return messages;
	}
	
	public synchronized Collection<Message> getFollowingMessages(String key, GetMessagesForm getMessagesForm) throws Exception {
		Collection<Message> messages;
		User user = getUser(key);
		if (user != null && user instanceof RolUV) {
			// Only RolUV users can send messages
			messages = ((RolUV)user).getFollowingMessages(getMessagesForm.getIdConversation(), getMessagesForm.getIdMessage());
		} else {
			throw new UnauthorizedException();
		}
		return messages;
	}

	public synchronized void updateToken(String key, String pushToken) throws Exception {
		User user = getUser(key);
		
		if (user != null && user instanceof RolUV) {
			((RolUV)user).setPushToken(pushToken);
		} else {
			throw new UnauthorizedException();
		}
	}

	public synchronized <T extends RolUV> Collection<RolUV> getUsers(String token) throws Exception {
		User user = getUser(token);
		if (user != null && user instanceof RolUV) {
				return ((RolUV)user).getUsers();
		} else {
			throw new UnauthorizedException();
		}
	}
	
	public synchronized void initConversation(String token, int userId) throws Exception {
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