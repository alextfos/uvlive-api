/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.models;

import es.uvlive.models.db.DBConnection;
import es.uvlive.models.users.User;
import es.uvlive.utils.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author atraverf
 */
public class UVLiveModel {
    private static UVLiveModel uvLiveModel;
    
    private DBConnection dbConnection;
    private SessionManager sessionManager;
    
    private static String STUDENT_TABLE = "Student";
    private static String TEACHER_TABLE = "Teacher";
    private static String MESSAGE_TABLE = "Message";
    private static String CONVERSATION_TABLE = "Conversation";
    private static String BUSINESSMAN_TABLE = "Businessman";
    private static String BROADCAST_TABLE = "Broadcast";
    private static String ADMINISTRATOR_TABLE = "Administrator";
    
    public UVLiveModel() {
        dbConnection = new DBConnection();
        sessionManager = new SessionManager();
    }
    public static synchronized UVLiveModel getInstance() {
        if(uvLiveModel == null) {
            uvLiveModel = new UVLiveModel();
        }
        return uvLiveModel;
    }

    public boolean login(String userName, String password, String loginType) {
        String table = "";
        switch(loginType) {
            case "Alumno":
                table = STUDENT_TABLE;
                break;
            case "Profesor":
                table = TEACHER_TABLE;
                break;
            case "Admin":
                table = ADMINISTRATOR_TABLE;
                break;
        }
        
        String sqlQuery = "SELECT * FROM "+table+" WHERE user = '"+userName+
                "' AND password = '"+password+"'";
        Logger.put("Debug login: "+sqlQuery);
        ResultSet result = dbConnection.query(sqlQuery);
        if(result!=null) {
            try {
                while(result.next()) {
                    String name = result.getString("user");
                    if(name.equals(userName))
                        return true;
                }
            } catch (SQLException ex) {
                Logger.put(this,"login - Error al recorrer el resultado de la consulta: " + ex.getMessage());
                return false;
            }
        }
        return false;
    }

    public ArrayList<Conversation> getConversations() {
        ArrayList<Conversation> arrayResult=new ArrayList<>();
        String sqlQuery = "SELECT * FROM "+CONVERSATION_TABLE;
        Logger.put(sqlQuery);
        ResultSet result = dbConnection.query(sqlQuery);
        if(result!=null) {
            try {
                Conversation tmpConversation;
                while(result.next()) {
                    String idConversation = result.getString("idConversation");
                    String name = result.getString("name");
                    tmpConversation = new Conversation(idConversation,name);
                    arrayResult.add(tmpConversation);
                }
            } catch (SQLException ex) {
                Logger.put(this,"getConversations - Error al recorrer el resultado de la consulta" + ex.getMessage());
            }
        }
        return arrayResult;
    }
    
    public synchronized User getUser (String key) {
        return sessionManager.getUser(key);
    }
    
    public synchronized void putUser (String key, User user) {
        sessionManager.putUser(key, user);
    }
    
    public synchronized boolean containsUser (String key) {
        return sessionManager.contains(key);
    }
}
