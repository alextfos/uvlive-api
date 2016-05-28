/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.model;

import es.uvlive.model.db.DBConnection;
import es.uvlive.utils.DebuggingUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author atraverf
 */
public class UVLiveModel {
    private static UVLiveModel uvLiveModel;
    
    private DBConnection dbConnection;
    
    private static String STUDENT_TABLE = "Student";
    private static String TEACHER_TABLE = "Teacher";
    private static String MESSAGE_TABLE = "Message";
    private static String CONVERSATION_TABLE = "Conversation";
    private static String BUSINESSMAN_TABLE = "Businessman";
    private static String BROADCAST_TABLE = "Broadcast";
    private static String ADMINISTRATOR_TABLE = "Administrator";
    
    public UVLiveModel(){
        dbConnection = new DBConnection();
    }
    public static UVLiveModel getUVLiveModel(){
        if(uvLiveModel==null){
            uvLiveModel=new UVLiveModel();
        }
        return uvLiveModel;
    }

    public boolean login(String userName, String password, String loginType) {
        String sqlQuery = "SELECT * FROM "+STUDENT_TABLE+" WHERE user = '"+userName+
                "' AND password = '"+password+"'";
        DebuggingUtils.log("Debug login: "+sqlQuery);
        ResultSet result = dbConnection.query(sqlQuery);
        if(result!=null) {
            try {
                while(result.next()) {
                    String name = result.getString("user");
                    if(name.equals(userName))
                        return true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(UVLiveModel.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("login - Error al recorrer el resultado de la consulta");
                return false;
            }
        }
        return false;
    }

    public ArrayList<Conversation> getConversations() {
        ArrayList<Conversation> arrayResult=new ArrayList<>();
        String sqlQuery = "SELECT * FROM "+CONVERSATION_TABLE;
        DebuggingUtils.log(sqlQuery);
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
                Logger.getLogger(UVLiveModel.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("getConversations - Error al recorrer el resultado de la consulta");
            }
        }
        return arrayResult;
    }
}
