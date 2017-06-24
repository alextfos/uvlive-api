package es.uvlive.model;

import java.math.BigInteger;
import java.security.Key;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import es.uvlive.api.GoogleInterface;
import es.uvlive.api.RetrofitFactory;
import es.uvlive.api.requests.NotificationRequest;
import es.uvlive.api.requests.Operation;
import es.uvlive.api.response.NotificationResponse;
import es.uvlive.exceptions.TokenExpiredException;
import es.uvlive.exceptions.WrongCredentialsException;
import es.uvlive.model.dao.MerchantDAO;
import es.uvlive.model.dao.UserDAO;
import es.uvlive.utils.Logger;
import es.uvlive.utils.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SessionManager {

	public static final Key SIGNATURE_KEY = MacProvider.generateKey();

	// TODO @Non-generated, VP overwrites with collection
	private HashMap<String, User> usersCollection;
	// TODO @Non-generated
	private SecureRandom random = new SecureRandom();

	public SessionManager() {
		usersCollection = new HashMap<>();
	}

	// TODO @Non-generated method
	/**
	 * 
	 * @param userName
	 * @param password
	 * @param loginType
	 */
	public String login(String userName, String password, String loginType, String pushToken, String token)
			throws Exception {

		User user = null;
		try {
			user = getUser(token);
		} catch (Exception e) {
			// Nothing to do here
		}

		if (user != null) {
			return token;
		}

		String newToken = null;
		UserDAO userDao = new UserDAO();
		user = userDao.getUser(userName, password, loginType);
		if (user != null) {
			String stringKey = userName + "-" + generateId();
			newToken = Jwts.builder().setSubject(stringKey).signWith(SignatureAlgorithm.HS512, SIGNATURE_KEY).compact();
			if (user instanceof Admin) {
				((Admin) user).setSessionManager(this);
			} else if (user instanceof RolUV) {
				((RolUV) user).setSessionManager(this);
			}
			if (user instanceof RolUV && !StringUtils.isEmpty(pushToken)) {
				((RolUV) user).setPushToken(pushToken);
			}
			usersCollection.put(stringKey, user);
		} else {
			throw new WrongCredentialsException();
		}
		return newToken;
	}

	// TODO @Non-generated method
	/**
	 * Logout
	 * 
	 * @param token
	 */
	public void logout(String token) {
		usersCollection.remove(token);
	}

	// TODO @Non-generated method
	/**
	 * Gets user - Si hay algun tipo de ptoblema con el token, se lanzará una
	 * excepción de no autorizado
	 * 
	 * @param key
	 */
	public User getUser(String token) throws TokenExpiredException {
		try {
			return usersCollection.get(getKey(token));
		} catch (Exception e) {
			throw new TokenExpiredException();
		}
	}

	private String getKey(String token) {
		Claims claims = Jwts.parser().setSigningKey(SIGNATURE_KEY).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	// TODO @Non-generated method
	/**
	 * Gets a new generated Id
	 * 
	 * @return generatedId
	 */
	private String generateId() {
		return new BigInteger(130, random).toString(32);
	}

	public boolean isUserExists(String userName) throws ClassNotFoundException, SQLException {
		return new UserDAO().getUser(userName) != null;
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
	 */
	public void registerMerchant(String dni, String firstname, String lastname, String username, String password)
			throws ClassNotFoundException, SQLException {
		new MerchantDAO().saveMerchant(dni, firstname, lastname, username, password);
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
	 */
	public void updateMerchant(String dni, String firstname, String lastname, String username, String password)
			throws ClassNotFoundException, SQLException {
		new MerchantDAO().updateMerchant(dni, firstname, lastname, username, password);
	}

	// TODO @Non-generated
	/**
	 * Blocks Student user by id
	 * 
	 * @param idStudent
	 */
	public void blockUser(int idStudent) {
		Set<String> users = usersCollection.keySet();

		for (String key : users) {
			User currentUser = usersCollection.get(key);
			if (currentUser.getUserId() == idStudent && currentUser instanceof Student) {
				((Student) usersCollection.get(key)).setBlocked(true);
			}
		}
	}

	// TODO @Non-generated
	/**
	 * Unblocks Student user by id
	 * 
	 * @param idStudent
	 */
	public void unblockUser(int idStudent) {
		Set<String> users = usersCollection.keySet();

		for (String key : users) {
			User currentUser = usersCollection.get(key);
			if (currentUser.getUserId() == idStudent && currentUser instanceof Student) {
				((Student) usersCollection.get(key)).setBlocked(false);
			}
		}
	}

	public void addConversationToUser(int userId, Tutorial tutorial) {
		Set<String> users = usersCollection.keySet();

		for (String key : users) {
			User currentUser = usersCollection.get(key);
			if (currentUser.getUserId() == userId && currentUser instanceof RolUV) {
				((RolUV) currentUser).getUserTutorials().add(tutorial);
				tutorial.addRolUV(((RolUV) currentUser));
				notifyConversationsChanged((RolUV) currentUser);
			}
		}
	}

	private void notifyConversationsChanged(final RolUV rolUV) {
		String pushToken = rolUV.getPushToken();
		GoogleInterface googleInterface = RetrofitFactory.getGoogleInterface();
		NotificationRequest notificationRequest = new NotificationRequest();

		notificationRequest.setData(new Operation("GET","CONVERSATIONS"));
		notificationRequest.setTo(pushToken);
		Call<NotificationResponse> callback = googleInterface.sendNotification(notificationRequest);
		callback.enqueue(new Callback<NotificationResponse>() {
			@Override
			public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> rspns) {
				NotificationResponse response = rspns.body();
				if (response.getFailure() > 0) {
					try {
						rolUV.removePushToken();
					} catch (Exception e) {
						e.printStackTrace();
						Logger.putError(SessionManager.this,e);
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

	public List<User> getUsers() {
		ArrayList<User> userArrayList = new ArrayList<>();
		userArrayList.addAll(usersCollection.values());

		return userArrayList;
	}
}