package es.uvlive.model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import es.uvlive.model.Merchant;

public class MerchantDAO extends BaseDAO {

	private static final String QUERY_SAVE_USER_MERCHANT = "INSERT INTO " + USER_TABLE + "(" + USER_NAME_FIELD + "," + PASSWORD_FIELD + "," + FIRST_NAME_FIELD + "," + LAST_NAME_FIELD + ") VALUES (?,?,?,?)";
	private static final String QUERY_SAVE_MERCHANT_MERCHANT = "INSERT INTO " + MERCHANT_TABLE + "(" + DNI_FIELD + ") VALUES (?)";
	private static final String QUERY_UPDATE_USER_MERCHANT = "UPDATE " + USER_TABLE + " SET " + USER_NAME_FIELD + "='%s', " + PASSWORD_FIELD + "='%s', "
	+ FIRST_NAME_FIELD + "='%s', " + LAST_NAME_FIELD + "='%s' WHERE " + USER_NAME_FIELD + "='%s'";
	private static final String QUERY_UPDATE_MERCHANT_MERCHANT = "UPDATE " + MERCHANT_TABLE + " SET " + DNI_FIELD + "='%s' WHERE " + USER_ID_FIELD + "=(SELECT " + USER_ID_FIELD + " FROM " + USER_TABLE + " WHERE " + USER_NAME_FIELD + "='%s')" ;
	private static final String QUERY_SAVE_BROADCAST = "INSERT INTO " + BROADCAST_TABLE + "(" + TITLE_FIELD + ", " + TEXT_FIELD + "," + BROADCAST_CREATION_TIMESTAMP + ", "+ BROADCAST_ID_MERCHANT_FIELD +") VALUES (?,?, ?, ?)";

	
	public void saveMerchant(String dni, String firstname, String lastname, String username, String password) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = getPreparedStatement(QUERY_SAVE_USER_MERCHANT);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, password);
		preparedStatement.setString(3, firstname);
		preparedStatement.setString(4, lastname);
		insert(preparedStatement);
		
		preparedStatement = getPreparedStatement(QUERY_SAVE_MERCHANT_MERCHANT);
		preparedStatement.setString(1, dni);
		insert(preparedStatement);
	}
	
	public void updateMerchant(String dni, String firstname, String lastname, String username, String password) throws ClassNotFoundException, SQLException {
		update(String.format(QUERY_UPDATE_USER_MERCHANT, username,password,firstname,lastname,username));
		update(String.format(QUERY_UPDATE_MERCHANT_MERCHANT,dni,username));
	}

	public void saveBroadcast(Merchant merchant, String title, String broadcastText) throws ClassNotFoundException, SQLException {
		int timestamp = (int) (((long) new Date().getTime()) / 1000);
		
		PreparedStatement preparedStatement = getPreparedStatement(QUERY_SAVE_BROADCAST);
		preparedStatement.setString(1,title);
		preparedStatement.setString(2, broadcastText);
		preparedStatement.setInt(3, timestamp);
		preparedStatement.setInt(4, merchant.getUserId());
		insert(preparedStatement);
	}
}
