package es.uvlive.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.uvlive.model.Businessman;
import es.uvlive.utils.Logger;

public class BusinessmanDAO extends BaseDAO {

	private static String QUERY_GET_BUSINESSMAN = "SELECT * FROM " + BUSINESSMAN_TABLE + "WHERE " + USER_NAME_FIELD + " ='%s'";
	private static String QUERY_SAVE_USER_BUSINESSMAN = "INSERT INTO " + USER_TABLE + "(" + USER_NAME_FIELD + "," + PASSWORD_FIELD + ") VALUES (?,?)";
	private static String QUERY_SAVE_BUSINESSMAN_BUSINESSMAN = "INSERT INTO " + BUSINESSMAN_TABLE + "(" + DNI_FIELD + ") VALUES (?)";
	private static String QUERY_UPDATE_BUSINESSMAN = "INSERT INTO " + BUSINESSMAN_TABLE + "(" + USER_NAME_FIELD + "," + PASSWORD_FIELD + ") VALUES ('%s','%s')";
	// UPDATE MyGuests SET lastname='Doe' WHERE id=2" TODO
	public Businessman getBusinessman(String userName) throws ClassNotFoundException, SQLException {
		Businessman businessman = null;
		ResultSet result = query(String.format(QUERY_GET_BUSINESSMAN,userName));
		
		if (result != null) {
            if (result.next()) {
            	businessman = new Businessman();
            	businessman.setUserId(result.getInt(USER_NAME_FIELD));
            }
        }
		
		return businessman;
	}
	
	public void saveBusinessman(String dni, String firstname, String lastname, String username, String password) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = getPreparedStatement(QUERY_SAVE_USER_BUSINESSMAN);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, password);
		insert(preparedStatement);
		
		preparedStatement = getPreparedStatement(QUERY_SAVE_BUSINESSMAN_BUSINESSMAN);
		preparedStatement.setString(1, dni);
		insert(preparedStatement);
	}
	
	public void updateBusinessman(String dni, String firstname, String lastname, String username, String password) throws ClassNotFoundException, SQLException {
		// TODO implement
		PreparedStatement preparedStatement = getPreparedStatement(QUERY_SAVE_USER_BUSINESSMAN);
		preparedStatement.setString(0, username);
		insert(preparedStatement);
	}
}
