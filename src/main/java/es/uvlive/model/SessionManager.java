package es.uvlive.model;

import es.uvlive.controllers.exceptions.WrongCredentialsException;
import es.uvlive.model.dao.BusinessmanDAO;
import es.uvlive.model.dao.UserDAO;
import es.uvlive.utils.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.math.BigInteger;
import java.security.Key;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.*;

public class SessionManager {
	
	public static final Key SIGNATURE_KEY =  MacProvider.generateKey();

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
    public String login(String userName, String password, String loginType, String pushToken, String key) throws Exception {
    	if (usersCollection.containsKey(key)) {
            return key;
        }
        String token = null;
        UserDAO userDao = new UserDAO();
        User user = userDao.getUser(userName, password, loginType);
        if (user != null) {
        	String stringKey = userName+"-"+generateId();
            token = Jwts.builder().setSubject(stringKey)
              .signWith(SignatureAlgorithm.HS512, SIGNATURE_KEY)
              .compact();
            if (user instanceof Admin) {
            	((Admin)user).setSessionManager(this);
            }
            if (user instanceof RolUV) {
            	((RolUV)user).setPushToken(pushToken);
            }
            usersCollection.put(stringKey, user);
        } else {
        	throw new WrongCredentialsException();
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
    	Claims claims = Jwts.parser()         
    		       .setSigningKey(SIGNATURE_KEY)
    		       .parseClaimsJws(key).getBody();
        return usersCollection.get(claims.getSubject());
    }
    
    // TODO @Non-generated method
    /**
     * Gets a new generated Id
     * @return generatedId
     */
    private String generateId() {
        return new BigInteger(130, random).toString(32);
    }

	public boolean isUserExists(String userName) throws ClassNotFoundException, SQLException {
		BusinessmanDAO businessmanDAO = new BusinessmanDAO();
		return businessmanDAO.getBusinessman(userName) != null;
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
	public void registerBusinessman(String dni, String firstname, String lastname, String username, String password) throws ClassNotFoundException, SQLException {
		new BusinessmanDAO().saveBusinessman(dni, firstname, lastname, username, password);
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
	public void updateBusinessman(String dni, String firstname, String lastname, String username, String password) throws ClassNotFoundException, SQLException {
		new BusinessmanDAO().updateBusinessman(dni, firstname, lastname, username, password);
	}
}