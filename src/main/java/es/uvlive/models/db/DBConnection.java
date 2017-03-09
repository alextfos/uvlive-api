/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.models.db;

import es.uvlive.utils.DebuggingUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author atraverf
 */
public class DBConnection {
    private Connection mConnectionDB;
    private static final String URL = "jdbc:mysql://localhost/uvlive";
    private static final String USER = "root";
    private static final String PASSWORD = "asdf1234";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    
    public DBConnection(){
        try {
            Class.forName(DRIVER);
            mConnectionDB = (Connection) DriverManager.getConnection(URL,USER, PASSWORD);
        } catch (SQLException ex) {
            DebuggingUtils.log(this,"SQLException in Database");
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            DebuggingUtils.log(this,"Exception not handled in Database: "+e.toString());
        }
        DebuggingUtils.log(this,"Database connected");
    }
    
    public ResultSet query(String sql) {
        try {
            return mConnectionDB.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY).executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            DebuggingUtils.log(this,"Exception while connect the database");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en la BD: "+e.toString());
            return null;
        }
    }
    
    public void logout(){
        try {
            if (mConnectionDB != null) {
                mConnectionDB.close();
                DebuggingUtils.log(this,"Disconnecting Database");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            DebuggingUtils.log (this,"Error connecting to Database");
        } catch (Exception e){
            DebuggingUtils.log (this,"Exception not handled in Database:"+e.toString());
        }
    }
}
