package es.uvlive.model.dao;

import es.uvlive.utils.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDAO {
    private static Connection sConnectionDB;
    // Debug environment
    private static final String URL = "jdbc:mysql://localhost/uvlive";
    private static final String USER = "root";
    private static final String PASSWORD = "asdf1234";
    private static final String DRIVER = "com.mysql.jdbc.Driver"; 
    
    public BaseDAO() {
        connect();
    }
    
    protected void connect() {
        try {
            Class.forName(DRIVER);
            sConnectionDB = (Connection) DriverManager.getConnection(URL,USER, PASSWORD);
            Logger.put(this,"Database connected");
        } catch (SQLException ex) {
            Logger.putError(this,"SQLException in Database: "+ex.getMessage());
        } catch (Exception e) {
            Logger.put(this,"Exception not handled in Database: "+e.toString());
        }
    }
    
    protected ResultSet query(String sql) {
        try {
            if (!(sConnectionDB != null && !sConnectionDB.isClosed())) {
                connect();
            }
            return sConnectionDB.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY).executeQuery(sql);
        } catch (SQLException ex) {
            Logger.put(this,"SQLException: "+ex.getMessage());
            return null;
        } catch (Exception e) {
            Logger.put(this,"Error en la BD: "+e.getMessage());
            return null;
        }
    }
    
    protected void disconnect() {
        try {
            if (sConnectionDB != null) {
                sConnectionDB.close();
                Logger.put(this,"Disconnecting Database");
            }
        } catch (SQLException ex) { 
           Logger.put(this,"Error disconnecting to Database: "+ex.getMessage());
        } catch (Exception e){
           Logger.put(this,"Exception not handled in Database: "+e.toString());
        }
    }
}
