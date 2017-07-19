package org.camunda.wf.hiring.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;

import javax.net.ssl.HttpsURLConnection;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.wf.hiring.dbAccess.*;

/*
 * This method is called during process execution and will execute the logic
 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
 */
public class SendPayment {

	public void execute(DelegateExecution arg0) throws Exception {
		// TODO Auto-generated method stub
		// Datenbank auslesen 
		// Daten ausrechnen
		
	}
	
	/*
	 * THIS METHOD IS ONLY FOR TEST REASONS!! TODO: DELETE CONTENT BEOFRE
	 * SUBMISSION
	 */
	public static void main(String[] args) {
		SendPayment run = new SendPayment();
		run.sendPaymentToProvider();
	}
	
	private double result;
	public void sendPaymentToProvider() {
		
		// Get DB connection
		DBAccess database = new DBAccess();
		
		try {
			result = database.getSumOfSalaries();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		  String url = "";
		  URL obj = new URL(url);
		  HttpURLConnection conection = (HttpURLConnection) obj.openConnection();
		 
		        // Setting basic post request
		  conection.setRequestMethod("POST");
		  //con.setRequestProperty("User-Agent", USER_AGENT);
		  conection.setRequestProperty("Content-Type","application/json");
		 
		  
		  String jsonData = "'{'salary':"+String.valueOf(result)+",'countryName':'USA','population':8000}'";
		  
		  // Send post request
		  conection.setDoOutput(true);
		  DataOutputStream wr = new DataOutputStream(conection.getOutputStream());
		  wr.writeBytes(jsonData);
		  wr.flush();
		  wr.close();
		 
		  int responseCode = conection.getResponseCode();
		  System.out.println("nSending 'POST' request to URL : " + url);
		  System.out.println("Post Data : " + jsonData);
		  System.out.println("Response Code : " + responseCode);
		 
		  BufferedReader in = new BufferedReader(
		          new InputStreamReader(conection.getInputStream()));
		  String output;
		  StringBuffer response = new StringBuffer();
		 
		  while ((output = in.readLine()) != null) {
		   response.append(output);
		  }
		  in.close();
		  
		  //printing result from response
		  System.out.println(response.toString());
		 }
		
		
		
		
		// Get all participants which have been accepted and have a salary
	
	}
}