package Coupons.Utils;

import java.sql.*;

public class JdbcUtils {

	private static String USERNAME = "root";
	private static String PASSWORD = "123456";
	//private static String PASSWORD = "administrator";
	private static String DRIVER = "com.mysql.cj.jdbc.Driver";
	//private static String DRIVER = "org.mariadb.jdbc.Driver";
	private static String DBDTYPE = "mysql";
	//private static String DBDTYPE = "mariadb";
	private static String DBDNAME = "jb";
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:"+DBDTYPE+"://localhost:3306/"+DBDNAME+"?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", USERNAME, PASSWORD);
		return connection;
	}

	public static void closeResources(Connection connection, PreparedStatement preparedStatement) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
		closeResources(connection, preparedStatement);
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}