package es.uvlive.model;

import es.uvlive.api.GoogleInterface;
import es.uvlive.api.RetrofitFactory;
import es.uvlive.api.requests.Notification;
import es.uvlive.api.requests.NotificationRequest;
import es.uvlive.api.response.NotificationResponse;
import es.uvlive.exceptions.ConversationNotCreatedException;
import es.uvlive.exceptions.UnauthorizedException;
import es.uvlive.model.dao.BroadcastDAO;
import es.uvlive.model.dao.MessageDAO;
import es.uvlive.utils.Logger;
import es.uvlive.utils.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
		Message.initMessageId(new MessageDAO().getMaxMessageId());
		conversationCatalog = new ConversationCatalog();
		sessionManager = new SessionManager();
	}

	/**
	*
	*/
	public synchronized void updateCourse() throws Exception {
		// Sync process
	}

    /**
     * 
     * @param userName
     * @param password
     * @param loginType
	 * @return token
	 * @throws Exception
     */
    public synchronized String login(String userName, String password, String loginType, String pushToken) throws Exception {
        String token = sessionManager.login(userName, password, loginType, pushToken);
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
    public synchronized void logout(String token) throws Exception{
        sessionManager.logout(token);
    }

    /**
     * 
     * @param key
     * @param idStudent
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
     * @param idStudent
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
	 * @throws Exception
	 */
	public synchronized User getUser(String key) throws Exception {
		return sessionManager.getUser(key);
	}

	/**
	 * 
	 * @param key
	 * @param userName
	 * @throws Exception
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
	 * @throws Exception
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
	 * @throws Exception
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
	 * @param title
	 * @param broadcastText
	 * @throws Exception
	 */
	public synchronized void registerBroadcast(String key, String title, String broadcastText) throws Exception {
		User user = getUser(key);
		if (user != null && user instanceof Merchant) {
			((Merchant)user).registerBroadcast(title, broadcastText);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public synchronized void sendBroadcasts() throws Exception {
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
							if (response != null && response.getFailure() > 0) {
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
	 * @throws Exception
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
	 * @throws Exception
	 */
	public synchronized void sendMessage(String key, int idConversation, String text) throws Exception {
		User user = getUser(key);
		if (user != null && user instanceof RolUV) {
			((RolUV)user).sendMessage(idConversation, text);
		} else {
			throw new UnauthorizedException();
		}
	}

	/**
	 *
	 * @param key
	 * @param idConversation
	 * @return messages
	 * @throws Exception
	 */
	public synchronized Collection<Message> getMessages(String key, int idConversation) throws Exception {
		Collection<Message> messages;
		User user = getUser(key);
		if (user != null && user instanceof RolUV) {
			messages = ((RolUV)user).getMessages(idConversation);
		} else {
			throw new UnauthorizedException();
		}
		return messages;
	}

	/**
	 *
	 * @param key
	 * @param idConversation
	 * @param idMessage
	 * @return messages
	 * @throws Exception
	 */
	public synchronized Collection<Message> getPreviousMessages(String key, int idConversation, int timestamp) throws Exception {
		Collection<Message> messages;
		User user = getUser(key);
		if (user != null && user instanceof RolUV) {
			messages = ((RolUV)user).getPreviousMessages(idConversation, timestamp);
		} else {
			throw new UnauthorizedException();
		}
		return messages;
	}
	
	public synchronized Collection<Message> getFollowingMessages(String key, int idConversation, int timestamp) throws Exception {
		Collection<Message> messages;
		User user = getUser(key);
		if (user != null && user instanceof RolUV) {
			messages = ((RolUV)user).getFollowingMessages(idConversation, timestamp);
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

	public Merchant getMerchant(String token, String merchantName) throws Exception {
		User user = getUser(token);
		if (user != null && user instanceof Admin) {
			return ((Admin)user).getMerchant(merchantName);
		} else {
			throw new UnauthorizedException();
		}
	}

	public String getOwnerField(String token) throws Exception {
		User user = getUser(token);
		String ownerField = "";
		if (user != null) {
			ownerField = user.getFirstname() + " " + user.getLastname();
		}
		return ownerField;
	}

}