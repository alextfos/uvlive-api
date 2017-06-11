package es.uvlive.model;

import es.uvlive.controllers.exceptions.TokenExpiredException;
import es.uvlive.controllers.exceptions.UnauthorizedException;
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
    public String login(String userName, String password, String loginType, String pushToken, String token) throws Exception {
    	if (!StringUtils.isEmpty(token) && usersCollection.containsKey(getKey(token))) {
            return token;
        }
        String newToken = null;
        UserDAO userDao = new UserDAO();
        User user = userDao.getUser(userName, password, loginType);
        if (user != null) {
        	String stringKey = userName+"-"+generateId();
        	newToken = Jwts.builder().setSubject(stringKey)
              .signWith(SignatureAlgorithm.HS512, SIGNATURE_KEY)
              .compact();
            if (user instanceof Admin) {
            	((Admin)user).setSessionManager(this);
            }
            if (user instanceof RolUV && !StringUtils.isEmpty(pushToken)) {
            	((RolUV)user).setPushToken(pushToken);
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
     * @param token
     */
    public void logout(String token) {
    	usersCollection.remove(token);
    }

   // TODO @Non-generated method
    /**
     * Gets user - Si hay algun tipo de ptoblema con el token, se lanzará una excepción de no autorizado
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
    	Claims claims = Jwts.parser()         
 		       .setSigningKey(SIGNATURE_KEY)
 		       .parseClaimsJws(token).getBody();
    	return claims.getSubject();
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

	// TODO @Non-generated
	/**
	 * Blocks Student user by id
	 * @param idStudent
	 */
	public void blockUser(int idStudent) {
		Set<String> users = usersCollection.keySet();
		
		for (String key : users) {
			User currentUser = usersCollection.get(key);
			if (currentUser.getUserId() == idStudent && currentUser instanceof Student) {
				((Student)usersCollection.get(key)).setBlocked(true);
			}
		}
	}
	
	// TODO @Non-generated
	/**
	 * Unblocks Student user by id
	 * @param idStudent
	 */
	public void unblockUser(int idStudent) {
		Set<String> users = usersCollection.keySet();
		
		for (String key : users) {
			User currentUser = usersCollection.get(key);
			if (currentUser.getUserId() == idStudent && currentUser instanceof Student) {
				((Student)usersCollection.get(key)).setBlocked(false);
			}
		}
	}
	
}