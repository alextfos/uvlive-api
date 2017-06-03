package es.uvlive.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.uvlive.model.Businessman;
import es.uvlive.utils.Logger;

public class BusinessmanDAO extends BaseDAO {
	
	private static String GET_BUSINESSMAN_QUERY = "SELECT * FROM " + BUSINESSMAN_TABLE + "WHERE user='%s'";
	private static String SAVE_BUSINESSMAN_QUERY = "INSERT INTO " + BUSINESSMAN_TABLE + "(user,password) VALUES (?,?)";
	private static String UPDATE_BUSINESSMAN_QUERY = "INSERT INTO " + BUSINESSMAN_TABLE + "(user,password) VALUES ('%s','%s')";
	public Businessman getBusinessman(String userName) {
		Businessman businessman = null;
		ResultSet result = query(String.format(GET_BUSINESSMAN_QUERY,userName));
		
		if (result != null) {
            try {
                if (result.next()) {
                	businessman = new Businessman();
                	businessman.setUser(result.getString("user"));
                }
            } catch (SQLException ex) {
                Logger.put(this,"BusinessmanDAO - Error getBusinessman SQL Exception: " + ex.getMessage());
                return null;
            }
        }
		
		return businessman;
	}
	
	public void saveBusinessman(String dni, String firstname, String lastname, String username, String password) {
		// TODO add fields to DATABASE structure
		try {
			// TODO implement
			PreparedStatement preparedStatement = getPreparedStatement(SAVE_BUSINESSMAN_QUERY);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			insert(preparedStatement);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void updateBusinessman(String dni, String firstname, String lastname, String username, String password) {
		try {
			// TODO implement
			PreparedStatement preparedStatement = getPreparedStatement(SAVE_BUSINESSMAN_QUERY);
			preparedStatement.setString(0, username);
			preparedStatement.setString(1, password);
			insert(preparedStatement);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
