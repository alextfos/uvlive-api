/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.models;

import es.uvlive.models.users.User;
import java.util.HashMap;

/**
 *
 * @author alextfos
 */
public class SessionManager {
    
    private HashMap<String,User> usersMap;
    
    public SessionManager() {
        usersMap = new HashMap();
    }
    
    public void putUser(String key, User user) {
        usersMap.put(key, user);
    }
    
    public void removeUser(String key) {
        usersMap.remove(key);
    }
    
    public User getUser(String key) {
        return usersMap.get(key);
    }
    
    public boolean contains(String key) {
        return usersMap.containsKey(key);
    }
}
