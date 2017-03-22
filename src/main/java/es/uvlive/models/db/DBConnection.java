/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.models.db;

import es.uvlive.utils.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author atraverf
 */
public class DBConnection {
    private Connection mConnectionDB;
    // Debug environment
    private static final String URL = "jdbc:mysql://localhost/uvlive";
    private static final String USER = "root";
    private static final String PASSWORD = "asdf1234";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    
    
    public DBConnection(){
        try {
            Class.forName(DRIVER);
            mConnectionDB = (Connection) DriverManager.getConnection(URL,USER, PASSWORD);
        } catch (SQLException ex) {
            Logger.putError(this,"SQLException in Database: "+ex.getMessage());
        } catch (Exception e) {
            Logger.put(this,"Exception not handled in Database: "+e.toString());
        }
        Logger.put(this,"Database connected");
    }
    
    public ResultSet query(String sql) {
        try {
            return mConnectionDB.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY).executeQuery(sql);
        } catch (SQLException ex) {
            Logger.put(this,"SQLException: "+ex.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Error en la BD: "+e.getMessage());
            return null;
        }
    }
    
    public void logout(){
        try {
            if (mConnectionDB != null) {
                mConnectionDB.close();
                Logger.put(this,"Disconnecting Database");
            }
        } catch (SQLException ex) { 
           Logger.put(this,"Error connecting to Database: "+ex.getMessage());
        } catch (Exception e){
           Logger.put(this,"Exception not handled in Database:"+e.toString());
        }
    }
}
