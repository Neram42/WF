package org.camunda.wf.hiring.dbAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class DBAccess {

	private static String url = "jdbc:mysql://localhost:3306/wfm";
	private static String username = "wfm";
	private static String password = "wfm2017";

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

	/*
	 *  This method is used to insert the interview into the Database
	 *  TODO: DELETE IF NO DATABASE ACCESS IS USED
	 */
	public static void addInterviewToDB(String instanceID, Calendar startdate, String status) throws SQLException {

		getConnection();

		Statement state = connection.createStatement();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		ResultSet rs = state.executeQuery("Select instanceID from interview WHERE interview.instanceID = '" + instanceID + "'");
		
		
		//Check if there is already a data set for the instanceID in the Database 
		//If not a new entry is set 
		if(rs.next() == false) {	
			String query = "INSERT INTO interview (instanceID, interviewDate, status) VALUES ('" + instanceID + "', '"
					+ sdf.format(new java.sql.Date(startdate.getTimeInMillis())) + "', '" + status + "');";
			System.out.println(query);
			state.executeUpdate(query);
		}
		//If there is already an entry it is updated
		else {
			String query = "UPDATE interview SET interviewDate = '" + sdf.format(new java.sql.Date(startdate.getTimeInMillis())) + "', instanceID ='" + instanceID +"';";
			System.out.println(query);
			state.executeUpdate(query);
		}
		
		state.close();
		connection.close();
	}
	
	/*
	 * This method updates the Status of the interview to the respective instanceID of the applicant
	 */
	public static void updateInterviewStatusToDB(String instanceID, String status) throws SQLException{
		getConnection();

		Statement state = connection.createStatement();

		String query = "INSERT INTO interview (status) VALUES ('" + status + "');";
		System.out.println(query);
		state.executeUpdate(query);
		
		state.close();
		connection.close();
	}
	
	
	/*
	 * TEST method
	 */
	public static void insertIntoJobOffer(String value) throws SQLException{
		getConnection();

		Statement state = connection.createStatement();

		String query = "INSERT INTO joboffer (departments) VALUES ('" + value + "');";
		System.out.println(query);
		state.executeUpdate(query);
		
		state.close();
		connection.close();
	}
	
	/*
	 *  This method is used to get the sum of all salaries of accepted applicants
	 */
		public double getSumOfSalaries() throws SQLException {

			getConnection();
			Statement state = connection.createStatement();
			
			String query = "SELECT SUM(potentialSalary) as sum FROM Interview WHERE status = 'accepted'";
			ResultSet result = state.executeQuery(query);
			result.next();
			
		
			
			return result.getDouble("sum");
		}

}
