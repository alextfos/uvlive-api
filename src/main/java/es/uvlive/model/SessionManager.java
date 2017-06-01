package es.uvlive.model;

import static es.uvlive.controllers.BaseController.SIGNATURE_KEY;
import es.uvlive.model.dao.UserDAO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

public class SessionManager {

	// TODO @Non-generated, VP overwrites with collection
    private HashMap<String,User> usersCollection;
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
    public String login(String userName, String password, String loginType, String key) {
    	if (usersCollection.containsKey(key)) {
            return key;
        }
        String token = null;
        UserDAO userDao = new UserDAO();
        User user = userDao.getUser(userName, password, loginType);
        if (user != null) {
            token = Jwts.builder().setSubject(userName+"-"+generateId())
              .signWith(SignatureAlgorithm.HS512, SIGNATURE_KEY)
              .compact();
            usersCollection.put(token, user);
        }
        return token;
    }
    
    // TODO @Non-generated method
    /**
     * Logout
     * @param token
     */
    public void logout(String token) {
    	usersCollection.remove(token);
    }

   // TODO @Non-generated method
    /**
     * Gets user
     * @param key
     */
    public User getUser(String key) {
        return usersCollection.get(key);
    }
    
    // TODO @Non-generated method
    /**
     * Gets a new generated Id
     * @return generatedId
     */
    private String generateId() {
        return new BigInteger(130, random).toString(32);
    }
}