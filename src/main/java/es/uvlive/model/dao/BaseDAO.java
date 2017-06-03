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
    protected static String CONVERSATION_STUDENT_TABLE = "Conversation_Student";
    protected static String CONVERSATION_TEACHER_TABLE = "Conversation_Teacher";
    
    public BaseDAO() {
    	try {
    		connect();
    	} catch (Exception e) {
    		System.err.println("Error intializing DAO object: " + e.getMessage());
    	}
    }
    
    protected void connect() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        sConnectionDB = (Connection) DriverManager.getConnection(URL,USER, PASSWORD);
        Logger.put(this,"Database connected");
    }
    
    protected ResultSet query(String sql) throws SQLException, ClassNotFoundException {
        if (!(sConnectionDB != null && !sConnectionDB.isClosed())) {
            connect();
        }
        return sConnectionDB.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY).executeQuery(sql);
    }
    
    
    protected PreparedStatement getPreparedStatement(String query) throws ClassNotFoundException, SQLException {
        if (!(sConnectionDB != null && !sConnectionDB.isClosed())) {
            connect();
        }
        return sConnectionDB.prepareStatement(query);
    }
    
    protected void insert(PreparedStatement preparedStatement) throws ClassNotFoundException, SQLException {
        if (!(sConnectionDB != null && !sConnectionDB.isClosed())) {
            connect();
        }
        preparedStatement.executeUpdate();
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
