package es.uvlive.model.dao;

import es.uvlive.model.UVLiveModel;
import es.uvlive.utils.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class BaseDAO {

    private static Connection sConnectionDB;
    // Debug environment
    private static final String URL = "jdbc:mysql://localhost/uvlive";
    private static final String USER = "root";
    private static final String PASSWORD = "asdf1234";
    private static final String DRIVER = "com.mysql.jdbc.Driver"; 
    
    /* Table names */
    protected static final String USER_TABLE = "User";
    protected static final String ROL_UV_TABLE = "ROLUV";
    protected static final String STUDENT_TABLE = "Student";
    protected static final String TEACHER_TABLE = "Teacher";
    protected static final String ADMIN_TABLE = "Admin";
    
    protected static final String MESSAGE_TABLE = "Message";
    protected static final String CONVERSATION_TABLE = "Conversation";
    protected static final String BUSINESSMAN_TABLE = "Businessman";
    protected static final String BROADCAST_TABLE = "Broadcast";
    
    protected static final String CONVERSATION_ROL_UV_TABLE = "Conversation_RolUV";
    
    /* Field names */
    protected static final String USER_ID_FIELD = "idUser";
    protected static final String USER_NAME_FIELD = "userName";
    protected static final String PASSWORD_FIELD = "password";
    protected static final String FIRST_NAME_FIELD = "firstName";
    protected static final String LAST_NAME_FIELD = "lastName";
    protected static final String DNI_FIELD = "dni";
    protected static final String PUSH_TOKEN_FIELD = "pushToken";
    protected static final String BLOCKED_FIELD = "blocked";
    
    protected static final String BRODCAST_ID_FIELD = "idBroadCast";
    protected static final String TEXT_FIELD = "text";
    protected static final String TIME_STAMP_FIELD = "timeStamp";
    protected static final String TUTORIAL_ID_FIELD = "idTutorial";
    protected static final String NAME_FIELD = "name";
    
    protected static final String CONVERSATION_ID_TUTORIAL_FIELD = "ConversationidTutorial";
    protected static final String ROL_UV_ID_USER_FIELD = "RolUVidUser";
    
    protected static final String MESSAGE_ID_CONVERSATION_FIELD = "ConversationIdConversation";
    protected static final String MESSAGE_ID_FIELD = "idMessage";
    
    protected static final String BROADCAST_ID_BUSINESSMAN_FIELD = "BusinessmanidBusinessman";
    protected static final String BROADCAST_EXPIRATION_DATE = "expiration_date";
    
    public BaseDAO() {
    	try {
    		connect();
    	} catch (Exception e) {
    		Logger.putError(this,e);
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
    
    protected void update(String query) throws SQLException {
    	java.sql.Statement statement = sConnectionDB.createStatement();
    	statement.executeUpdate(query);
    }
    
    protected void disconnect() {
        try {
            if (sConnectionDB != null) {
                sConnectionDB.close();
                Logger.put(this,"Database disconnected");
            }
        } catch (SQLException ex) { 
           Logger.putError(this,"SQL Error disconnecting to Database: "+ex.getMessage());
        } catch (Exception e){
           Logger.putError(this,"Exception disconnecting to Database: "+e.toString());
        }
    }
}
