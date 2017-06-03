package es.uvlive.model.dao;

import es.uvlive.utils.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDAO {
	// TODO @Non-generated (Hint: nothing on this class is from VP)
    private static Connection sConnectionDB;
    // Debug environment
    private static final String URL = "jdbc:mysql://localhost/uvlive";
    private static final String USER = "root";
    private static final String PASSWORD = "asdf1234";
    private static final String DRIVER = "com.mysql.jdbc.Driver"; 
    
    protected static String STUDENT_TABLE = "Student";
    protected static String TEACHER_TABLE = "Teacher";
    protected static String MESSAGE_TABLE = "Message";
    protected static String CONVERSATION_TABLE = "Conversation";
    protected static String BUSINESSMAN_TABLE = "Businessman";
    protected static String BROADCAST_TABLE = "Broadcast";
    protected static String ADMINISTRATOR_TABLE = "Administrator";
    
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
    
    
    protected PreparedStatement getPreparedStatement(String query) {
    	try {
            if (!(sConnectionDB != null && !sConnectionDB.isClosed())) {
                connect();
            }
            return sConnectionDB.prepareStatement(query);
        } catch (SQLException ex) {
            Logger.put(this,"SQLException: "+ex.getMessage());
            return null;
        } catch (Exception e) {
            Logger.put(this,"Error en la BD: "+e.getMessage());
            return null;
        }
    }
    
    protected void insert(PreparedStatement preparedStatement) {
    	try {
            if (!(sConnectionDB != null && !sConnectionDB.isClosed())) {
                connect();
            }
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.put(this,"SQLException: "+ex.getMessage());
        } catch (Exception e) {
            Logger.put(this,"Error en la BD: "+e.getMessage());
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
