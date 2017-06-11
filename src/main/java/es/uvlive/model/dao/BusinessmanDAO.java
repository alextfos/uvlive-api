package es.uvlive.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import es.uvlive.model.Businessman;
import es.uvlive.utils.Logger;

public class BusinessmanDAO extends BaseDAO {
	
	private static String QUERY_GET_BUSINESSMAN = "SELECT * FROM " + USER_TABLE + " NATURAL JOIN " + BUSINESSMAN_TABLE + " WHERE " + USER_NAME_FIELD + " ='%s'";
	private static String QUERY_SAVE_USER_BUSINESSMAN = "INSERT INTO " + USER_TABLE + "(" + USER_NAME_FIELD + "," + PASSWORD_FIELD + "," + FIRST_NAME_FIELD + "," + LAST_NAME_FIELD + ") VALUES (?,?,?,?)";
	private static String QUERY_SAVE_BUSINESSMAN_BUSINESSMAN = "INSERT INTO " + BUSINESSMAN_TABLE + "(" + DNI_FIELD + ") VALUES (?)";
	private static String QUERY_UPDATE_USER_BUSINESSMAN = "UPDATE " + USER_TABLE + " SET " + USER_NAME_FIELD + "='%s', " + PASSWORD_FIELD + "='%s', " 
	+ FIRST_NAME_FIELD + "='%s', " + LAST_NAME_FIELD + "='%s' WHERE " + USER_NAME_FIELD + "='%s'";
	private static String QUERY_UPDATE_BUSINESSMAN_BUSINESSMAN = "UPDATE " + BUSINESSMAN_TABLE + " SET " + DNI_FIELD + "='%s' WHERE " + USER_ID_FIELD + "=(SELECT " + USER_ID_FIELD + " FROM " + USER_TABLE + " WHERE " + USER_NAME_FIELD + "='%s')" ;
	
	private static String QUERY_SAVE_BROADCAST = "INSERT INTO " + BROADCAST_TABLE + "(" + TEXT_FIELD + "," + BROADCAST_ID_BUSINESSMAN_FIELD + ", "+ BROADCAST_EXPIRATION_DATE +") VALUES (?, ?, ?)";
	
	// UPDATE MyGuests SET lastname='Doe' WHERE id=2" TODO
	public Businessman getBusinessman(String userName) throws ClassNotFoundException, SQLException {
		Businessman businessman = null;
		ResultSet result = query(String.format(QUERY_GET_BUSINESSMAN,userName));
		
		if (result != null) {
            if (result.next()) {
            	businessman = new Businessman();
            	businessman.setUserId(result.getInt(USER_ID_FIELD));
            	businessman.setUsername(result.getString(USER_NAME_FIELD));
            	businessman.setFirstname(result.getString(FIRST_NAME_FIELD));
            	businessman.setLastname(result.getString(LAST_NAME_FIELD));
            	businessman.setDni(result.getString(DNI_FIELD));
            }
        }
		
		return businessman;
	}
	
	public void saveBusinessman(String dni, String firstname, String lastname, String username, String password) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = getPreparedStatement(QUERY_SAVE_USER_BUSINESSMAN);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, password);
		preparedStatement.setString(3, firstname);
		preparedStatement.setString(4, lastname);
		insert(preparedStatement);
		
		preparedStatement = getPreparedStatement(QUERY_SAVE_BUSINESSMAN_BUSINESSMAN);
		preparedStatement.setString(1, dni);
		insert(preparedStatement);
	}
	
	public void updateBusinessman(String dni, String firstname, String lastname, String username, String password) throws ClassNotFoundException, SQLException {
		update(String.format(QUERY_UPDATE_USER_BUSINESSMAN, username,password,firstname,lastname,username));
		update(String.format(QUERY_UPDATE_BUSINESSMAN_BUSINESSMAN,dni,username));
	}

	// TODO @check in VP (Hint: nothing of broadcast usecase is implemented)
	public void registerBroadcast(Businessman businessman, String broadcastText) throws ClassNotFoundException, SQLException {
		String timestamp = String.valueOf(((long) new Date().getTime()) / 1000);
		
		PreparedStatement preparedStatement = getPreparedStatement(QUERY_SAVE_BROADCAST);
		preparedStatement.setString(1, broadcastText);
		preparedStatement.setInt(2, businessman.getUserId());
		preparedStatement.setString(3, timestamp);
		insert(preparedStatement);
	}
}
