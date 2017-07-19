package org.camunda.wf.hiring.dbAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class DBAccess {

	private static String url = "jdbc:mysql://localhost:3306/d0270820";
	private static String username = "root";
	private static String password = "root";

	private static Connection connection;

	public DBAccess() {

		// Access on the localhost database
		try {
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Database connected!");

		} catch (Exception e) {

		}
	}

	public static Connection getConnection() {
		if (connection == null)
			new DBAccess();
		return connection;
	}

	// This method is used to insert
	public static void addInterviewToDB(String instanceID, Calendar startdate, String status) throws SQLException {

		getConnection();

		Statement state = connection.createStatement();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String query = "INSERT INTO interview (instanceID, interviewDate, status) VALUES ('" + instanceID + "', '"
				+ sdf.format(new java.sql.Date(startdate.getTimeInMillis())) + "', '" + status + "');";
		System.out.println(query);
		state.executeUpdate(query);
		
		state.close();
		connection.close();
	}

}
