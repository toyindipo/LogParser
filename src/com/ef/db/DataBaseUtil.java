package com.ef.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseUtil {
	public static Connection getConnection(String url, String user, String passwd) 
			throws SQLException {	
            Connection conn = DriverManager.getConnection(url, user, passwd);
            return conn;
	}
	
	public static PreparedStatement getPreparedStatement
		(Connection connection, String tableName) throws SQLException {
		String statementStr = String.format("INSERT INTO %s(ipAddress, comment) VALUES(?, ?)", 
				tableName);
		return connection.prepareStatement(statementStr);
	}
	
	public static void insertIntoTable(Connection conn, PreparedStatement statement, 
			String ipAddress, String comment) throws SQLException {
		statement.setString(1, ipAddress);
		statement.setString(2, comment);
		statement.executeUpdate();
	}
}
