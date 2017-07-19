package ord.camunda.wf.hiring.dbAccess;

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
	
	
	public static Connection getConnection(){
		if(connection==null)
			new DBAccess();
		return connection; 
	}
	
	//This method is used to insert 
	public static void addInterviewToDB(String instanceID, Calendar startdate, int status) throws SQLException{
		
		getConnection();
		
		Statement state = connection.createStatement();
		
//	    ResultSet rs = state.executeQuery("INSERT into d0270820.cv (0) values (testid)");
		state.executeUpdate("INSERT INTO interview (instanceID, interviewDate, status) VALUES ("+ instanceID + ", " + new java.sql.Date(startdate.getTimeInMillis()) + ", "+ status +");");

	    
//	    while (rs.next()) {
	    	// Get the data from the row using the column index
//	    	 String s = rs.getString(1); 
//	    	System.out.println("Ausgabe: " + s);
//			ResultSet saveDate = state.executeQuery("INSERT into Interview (instanceID, interviewDate) values ("+ rs.getString(1) + ", " + startdate + ")");
//	    	}


//		rs.close();
		
//		saveDate.close();
		
		connection.close();
	}
	
	
}
