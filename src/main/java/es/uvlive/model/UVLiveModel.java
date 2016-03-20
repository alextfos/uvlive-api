/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.model;

import es.uvlive.model.db.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author atraverf
 */
public class UVLiveModel {
    private static UVLiveModel uvLiveModel;
    
    private DBConnection dbConnection;
    
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
        return true;
        /*String sqlQuery = "SELECT * FROM usuarios WHERE nombre = "+userName+
                " AND password = "+password;
        ResultSet result = dbConnection.query(sqlQuery);
        if(result!=null){
            try {
                while(result.next()){
                    String name = result.getString("nombre");
                    if(name.equals(userName))
                        return true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(UVLiveModel.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error al recorrer el resultado de la consulta");
                return false;
            }
        }
        return false;*/
    }
}
