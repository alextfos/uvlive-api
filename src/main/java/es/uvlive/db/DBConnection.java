/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.db;

import java.sql.Connection;
import java.sql.DriverManager;
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
    private static final String PASSWORD = "05+tr*inwXG*";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    
    public DBConnection(){
        try {
            Class.forName(DRIVER);
            mConnectionDB = (Connection) DriverManager.getConnection(URL,USER, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error de Conexión en la BD");
        } catch (Exception e){
            System.out.println("Error en la BD:"+e.toString());
        }
    }
    
    public void logout(){
        try {
            if (mConnectionDB != null) {
                mConnectionDB.close();
                System.out.println("Desconectado de la BD");
            } else {
                System.out.println("La conexion ya estaba cerrada");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error de Conexión en la BD");
        } catch (Exception e){
            System.out.println("Error en la BD:"+e.toString());
        }
    }
}
