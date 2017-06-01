package es.uvlive.model;

import static es.uvlive.controllers.BaseController.SIGNATURE_KEY;
import es.uvlive.model.dao.UserDAO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

public class SessionManager {

    private HashMap<String,User> coleccionUsuarios;
    private SecureRandom random = new SecureRandom();

    public SessionManager() {
        coleccionUsuarios = new HashMap<>();
    }

    /**
     * 
     * @param userName
     * @param password
     * @param loginType
     */
    public String login(String userName, String password, String loginType, String key) {
        if (coleccionUsuarios.containsKey(key)) {
            return key;
        }
        String token = null;
        UserDAO userDao = new UserDAO();
        User user = userDao.getUser(userName, password, loginType);
        if (user != null) {
            token = Jwts.builder().setSubject(userName+"-"+generateId())
              .signWith(SignatureAlgorithm.HS512, SIGNATURE_KEY)
              .compact();
            coleccionUsuarios.put(token, user);
        }
        return token;
    }
    
    /**
     * Logout
     * @param token
     */
    public void logout(String token) {
    	coleccionUsuarios.remove(token);
    }

    /**
     * 
     * @param key
     */
    public User getUser(String key) {
        return coleccionUsuarios.get(key);
    }
    
    private String generateId() {
        return new BigInteger(130, random).toString(32);
    }
}