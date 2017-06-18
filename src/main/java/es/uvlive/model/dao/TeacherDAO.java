package es.uvlive.model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TeacherDAO extends BaseDAO {
	private static final String QUERY_UPDATE_USER_STATUS = "UPDATE " + STUDENT_TABLE + " SET " + BLOCKED_FIELD + " = ? WHERE " + USER_ID_FIELD + "= ? ";
		private static final int UNBLOCKED = 0;
		private static final int BLOCKED = 1;
		
		// TODO @Non-generated
		/**
		 * Changes Student status to blocked
		 * @throws ClassNotFoundException
		 * @throws SQLException
		 */
		public void bockStudent(int idStudent) throws ClassNotFoundException, SQLException {
			PreparedStatement preparedStatement = getPreparedStatement(QUERY_UPDATE_USER_STATUS);
			preparedStatement.setInt(1, BLOCKED);
			preparedStatement.setInt(2, idStudent);
			insert(preparedStatement);
		}
		
		// TODO @Non-generated
		/**
		 * Changes Student status to unblocked
		 * @param idStudent
		 * @throws ClassNotFoundException
		 * @throws SQLException
		 */
		public void unblockStudent(int idStudent) throws ClassNotFoundException, SQLException {
			PreparedStatement preparedStatement = getPreparedStatement(QUERY_UPDATE_USER_STATUS);
			preparedStatement.setInt(1, UNBLOCKED);
			preparedStatement.setInt(2, idStudent);
			insert(preparedStatement);
		}
}
