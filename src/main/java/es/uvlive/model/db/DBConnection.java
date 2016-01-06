/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.model.db;

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
    private static final String URL = "jdbc:mysql://localhost/prueba";
    private static final String USER = "root";
    private static final String PASSWORD = "asdf1234";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    
    public DBConnection(){
        try {
            Class.forName(DRIVER);
            mConnectionDB = (Connection) DriverManager.getConnection(URL,USER, PASSWORD);
        } catch (SQLException ex) {
            System.out.println("Error de Conexión en la BD");
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            System.out.println("Error en la BD: "+e.toString());
        }
    }
    
    public ResultSet query(String sql) {
        try {
            return mConnectionDB.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY).executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error de Conexión en la BD");
            return null;
        } catch (Exception e) {
            System.out.println("Error en la BD: "+e.toString());
            return null;
        }
    }
    
    public void logout(){
        try {
            if (mConnectionDB != null) {
                mConnectionDB.close();
                System.out.println("Desconectado de la BD");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error de Conexión en la BD");
        } catch (Exception e){
            System.out.println("Error en la BD:"+e.toString());
        }
    }
}
